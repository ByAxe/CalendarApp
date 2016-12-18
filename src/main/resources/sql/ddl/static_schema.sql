-- ----------------------------
-- DATABASE
-- ----------------------------
DROP DATABASE IF EXISTS calendar;
CREATE DATABASE calendar;

-- ----------------------------
-- SCHEMA
-- ----------------------------
DROP SCHEMA IF EXISTS cld CASCADE;
CREATE SCHEMA IF NOT EXISTS cld;

SET SEARCH_PATH TO cld, public;

-- ----------------------------
-- TABLE events
-- ----------------------------
DROP TABLE IF EXISTS cld.events;
CREATE TABLE cld.events (
  id            BIGSERIAL PRIMARY KEY,
  uuid          UUID           NOT NULL,
  dt_update     TIMESTAMPTZ(6) NOT NULL,
  starts        TIMESTAMPTZ(6) NOT NULL,
  ends          TIMESTAMPTZ(6) NOT NULL,
  title         VARCHAR(150)   NOT NULL,
  notice_period VARCHAR        NOT NULL DEFAULT 'FIFTEEN_MINUTES',
  priority      VARCHAR        NOT NULL DEFAULT 'BASIC',
  frequency     VARCHAR        NOT NULL DEFAULT 'NORMAL'
);

CREATE UNIQUE INDEX events_uuid_idx
  ON cld.events USING BTREE (uuid);

-- ----------------------------
-- TABLE groups
-- ----------------------------
DROP TABLE IF EXISTS cld.groups;
CREATE TABLE cld.groups (
  id             BIGSERIAL PRIMARY KEY,
  uuid           UUID           NOT NULL,
  dt_update      TIMESTAMPTZ(6) NOT NULL,
  title          VARCHAR(100)   NOT NULL,
  specialization VARCHAR,
  qualification  VARCHAR        NOT NULL,
  description    TEXT,
  number         VARCHAR(10)    NOT NULL,
  hours          INTEGER        NOT NULL,
  ruler_id       BIGINT         NOT NULL
);

CREATE UNIQUE INDEX groups_uuid_idx
  ON cld.groups USING BTREE (uuid);

-- ----------------------------
-- TABLE rulers
-- ----------------------------
DROP TABLE IF EXISTS cld.rulers;
CREATE TABLE cld.rulers (
  id          BIGSERIAL PRIMARY KEY,
  uuid        UUID           NOT NULL,
  dt_update   TIMESTAMPTZ(6) NOT NULL,
  first_name  VARCHAR(100)   NOT NULL,
  middle_name VARCHAR(100)   NOT NULL,
  last_name   VARCHAR(100)   NOT NULL,
  payload     TEXT
);

CREATE UNIQUE INDEX rulers_uuid_idx
  ON cld.rulers USING BTREE (uuid);

-- ----------------------------
-- TABLE students
-- ----------------------------
DROP TABLE IF EXISTS cld.students;
CREATE TABLE cld.students (
  id          BIGSERIAL PRIMARY KEY,
  uuid        UUID           NOT NULL,
  dt_update   TIMESTAMPTZ(6) NOT NULL,
  first_name  VARCHAR(100)   NOT NULL,
  middle_name VARCHAR(100)   NOT NULL,
  last_name   VARCHAR(100)   NOT NULL,
  telephone   VARCHAR(100),
  address     VARCHAR(150)   NOT NULL
);

CREATE UNIQUE INDEX students_uuid_idx
  ON cld.students USING BTREE (uuid);

-- ----------------------------
-- TABLE organisations
-- ----------------------------
DROP TABLE IF EXISTS cld.organisations;
CREATE TABLE cld.organisations (
  id        BIGSERIAL PRIMARY KEY,
  uuid      UUID           NOT NULL,
  dt_update TIMESTAMPTZ(6) NOT NULL,
  title     VARCHAR(100)   NOT NULL,
  address   VARCHAR(150)   NOT NULL,
  telephone VARCHAR(100)   NOT NULL,
  contacts  TEXT
);

CREATE UNIQUE INDEX organisations_uuid_idx
  ON cld.organisations USING BTREE (uuid);

-- ----------------------------
-- TABLE orders
-- ----------------------------
DROP TABLE IF EXISTS cld.orders;
CREATE TABLE cld.orders (
  id         BIGSERIAL PRIMARY KEY,
  uuid       UUID           NOT NULL,
  dt_update  TIMESTAMPTZ(6) NOT NULL,
  profession VARCHAR(100)   NOT NULL,
  rank       INT            NOT NULL DEFAULT 1,
  starts     TIMESTAMPTZ(6) NOT NULL,
  number     VARCHAR(50)    NOT NULL
);

CREATE UNIQUE INDEX orders_uuid_idx
  ON cld.orders USING BTREE (uuid);

-- ----------------------------
-- TABLE compensations
-- ----------------------------
DROP TABLE IF EXISTS cld.compensations;
CREATE TABLE cld.compensations (
  id                BIGSERIAL PRIMARY KEY,
  uuid              UUID           NOT NULL,
  dt_update         TIMESTAMPTZ(6) NOT NULL,
  cort              BOOLEAN        NOT NULL DEFAULT FALSE,
  order_date        TIMESTAMPTZ(6) NOT NULL,
  order_number      VARCHAR(50)    NOT NULL,
  confirmation_date TIMESTAMPTZ(6) NOT NULL
);

CREATE UNIQUE INDEX compensations_uuid_idx
  ON cld.compensations USING BTREE (uuid);

-- ----------------------------
-- TABLE allocation
-- ----------------------------
DROP TABLE IF EXISTS cld.allocation;
CREATE TABLE cld.allocation (
  id                     BIGSERIAL PRIMARY KEY,
  uuid                   UUID           NOT NULL,
  dt_update              TIMESTAMPTZ(6) NOT NULL,
  parent_uuid            UUID,
  group_id               BIGINT         NOT NULL,
  organisation_id        BIGINT         NOT NULL,
  student_id             BIGINT         NOT NULL,
  order_id               BIGINT,
  compensation_id        BIGINT,
  confirmation           VARCHAR ARRAY,
  army                   BOOLEAN        NOT NULL DEFAULT FALSE,
  stage                  VARCHAR        NOT NULL DEFAULT 'FIRST',
  free_allocation        BOOLEAN        NOT NULL DEFAULT FALSE,
  free_allocation_reason TEXT
);

CREATE UNIQUE INDEX allocation_uuid_idx
  ON cld.allocation USING BTREE (uuid);

-- ----------------------------
-- Foreign Key structure for table cld.groups
-- ----------------------------
ALTER TABLE cld.groups
  ADD FOREIGN KEY (ruler_id) REFERENCES cld.rulers;

-- ----------------------------
-- Foreign Key structure for table cld.allocation
-- ----------------------------
ALTER TABLE cld.allocation
  ADD FOREIGN KEY (group_id) REFERENCES cld.groups;

ALTER TABLE cld.allocation
  ADD FOREIGN KEY (organisation_id) REFERENCES cld.organisations;

ALTER TABLE cld.allocation
  ADD FOREIGN KEY (student_id) REFERENCES cld.students;

ALTER TABLE cld.allocation
  ADD FOREIGN KEY (order_id) REFERENCES cld.orders;

ALTER TABLE cld.allocation
  ADD FOREIGN KEY (compensation_id) REFERENCES cld.compensations;
