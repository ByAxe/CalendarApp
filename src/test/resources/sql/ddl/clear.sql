-- ----------------------------
-- DATABASE
-- ----------------------------
DROP DATABASE IF EXISTS calendar_test;
CREATE DATABASE calendar_test;

-- ----------------------------
-- SCHEMA
-- ----------------------------
DROP SCHEMA IF EXISTS cld_test CASCADE;
CREATE SCHEMA IF NOT EXISTS cld_test;

SET SEARCH_PATH TO cld_test, public;

-- ----------------------------
-- TABLE events
-- ----------------------------
DROP TABLE IF EXISTS cld_test.events;
CREATE TABLE cld_test.events (
  id            BIGSERIAL,
  uuid          UUID           NOT NULL,
  dt_update     TIMESTAMPTZ(6) NOT NULL,
  starts        TIMESTAMPTZ(6) NOT NULL,
  ends          TIMESTAMPTZ(6) NOT NULL,
  title         VARCHAR(150)   NOT NULL,
  notice_period VARCHAR        NOT NULL DEFAULT 'NONE',
  priority      VARCHAR        NOT NULL DEFAULT 'BASIC',
  frequency     VARCHAR        NOT NULL DEFAULT 'ONCE'
);

CREATE UNIQUE INDEX events_id_idx
  ON cld_test.events USING BTREE (id);
CREATE UNIQUE INDEX events_uuid_idx
  ON cld_test.events USING BTREE (uuid);

-- ----------------------------
-- TABLE groups
-- ----------------------------
DROP TABLE IF EXISTS cld_test.groups;
CREATE TABLE cld_test.groups (
  id             BIGSERIAL,
  uuid           UUID           NOT NULL,
  dt_update      TIMESTAMPTZ(6) NOT NULL,
  title          VARCHAR(100)   NOT NULL,
  specialization VARCHAR,
  qualification  VARCHAR        NOT NULL,
  description    TEXT,
  number         VARCHAR(10)    NOT NULL,
  hours          INTEGER        NOT NULL
);

CREATE UNIQUE INDEX groups_id_idx
  ON cld_test.groups USING BTREE (id);
CREATE UNIQUE INDEX groups_uuid_idx
  ON cld_test.groups USING BTREE (uuid);
