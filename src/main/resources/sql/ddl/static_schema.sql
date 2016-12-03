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
  id            BIGSERIAL,
  uuid          UUID           NOT NULL,
  dt_update     TIMESTAMPTZ(6) NOT NULL,
  starts        TIMESTAMPTZ(6) NOT NULL,
  ends          TIMESTAMPTZ(6) NOT NULL,
  title         VARCHAR(150)   NOT NULL,
  notice_period VARCHAR        NOT NULL DEFAULT 'NONE',
  priority      VARCHAR        NOT NULL DEFAULT 'BASIC',
  frequency     VARCHAR        NOT NULL DEFAULT 'ANNUALLY'
);

CREATE UNIQUE INDEX events_id_idx
  ON cld.events USING BTREE (id);
CREATE UNIQUE INDEX events_uuid_idx
  ON cld.events USING BTREE (uuid);

-- ----------------------------
-- TABLE groups
-- ----------------------------
DROP TABLE IF EXISTS cld.groups;
CREATE TABLE cld.groups (
  id             BIGSERIAL,
  uuid           UUID           NOT NULL,
  dt_update      TIMESTAMPTZ(6) NOT NULL,
  title          VARCHAR(100)   NOT NULL,
  specialization VARCHAR,
  description    TEXT,
  number         VARCHAR(10)    NOT NULL,
  hours          INTEGER        NOT NULL
);

CREATE UNIQUE INDEX groups_id_idx
  ON cld.groups USING BTREE (id);
CREATE UNIQUE INDEX groups_uuid_idx
  ON cld.groups USING BTREE (uuid);
