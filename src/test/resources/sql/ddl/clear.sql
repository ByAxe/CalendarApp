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
  ON cld_test.events USING BTREE (uuid);

-- ----------------------------
-- TABLE groups
-- ----------------------------
DROP TABLE IF EXISTS cld_test.groups;
CREATE TABLE cld_test.groups (
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
  ON cld_test.groups USING BTREE (uuid);

-- ----------------------------
-- TABLE rulers
-- ----------------------------
DROP TABLE IF EXISTS cld_test.rulers;
CREATE TABLE cld_test.rulers (
  id          BIGSERIAL PRIMARY KEY,
  uuid        UUID           NOT NULL,
  dt_update   TIMESTAMPTZ(6) NOT NULL,
  first_name  VARCHAR(100)   NOT NULL,
  middle_name VARCHAR(100)   NOT NULL,
  last_name   VARCHAR(100)   NOT NULL,
  payload     TEXT
);

CREATE UNIQUE INDEX rulers_uuid_idx
  ON cld_test.rulers USING BTREE (uuid);

-- ----------------------------
-- TABLE students
-- ----------------------------
DROP TABLE IF EXISTS cld_test.students;
CREATE TABLE cld_test.students (
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
  ON cld_test.students USING BTREE (uuid);

-- ----------------------------
-- TABLE organisations
-- ----------------------------
DROP TABLE IF EXISTS cld_test.organisations;
CREATE TABLE cld_test.organisations (
  id        BIGSERIAL PRIMARY KEY,
  uuid      UUID           NOT NULL,
  dt_update TIMESTAMPTZ(6) NOT NULL,
  title     VARCHAR(100)   NOT NULL,
  address   VARCHAR(150)   NOT NULL,
  telephone VARCHAR(100)   NOT NULL,
  contacts  TEXT
);

CREATE UNIQUE INDEX organisations_uuid_idx
  ON cld_test.organisations USING BTREE (uuid);

-- ----------------------------
-- TABLE orders
-- ----------------------------
DROP TABLE IF EXISTS cld_test.orders;
CREATE TABLE cld_test.orders (
  id         BIGSERIAL PRIMARY KEY,
  uuid       UUID           NOT NULL,
  dt_update  TIMESTAMPTZ(6) NOT NULL,
  profession VARCHAR(100)   NOT NULL,
  rank       INT            NOT NULL DEFAULT 1, -- Разряд
  starts     TIMESTAMPTZ(6) NOT NULL, -- Дата приказа
  number     VARCHAR(50)    NOT NULL, -- Номер приказа
  payload    TEXT
);

CREATE UNIQUE INDEX orders_uuid_idx
  ON cld_test.orders USING BTREE (uuid);

-- ----------------------------
-- TABLE allocation
-- ----------------------------
DROP TABLE IF EXISTS cld_test.allocation;
CREATE TABLE cld_test.allocation (
  id                                       BIGSERIAL PRIMARY KEY,
  uuid                                     UUID           NOT NULL,
  dt_update                                TIMESTAMPTZ(6) NOT NULL,
  parent_uuid                              UUID,
  group_id                                 BIGINT         NOT NULL,
  organisation_id                          BIGINT         NOT NULL,
  student_id                               BIGINT         NOT NULL,
  order_id                                 BIGINT,
  confirmations                            VARCHAR ARRAY, -- Подтверждения об отработке
  army                                     BOOLEAN        NOT NULL DEFAULT FALSE,
  stage                                    VARCHAR        NOT NULL DEFAULT 'FIRST', -- Ступень образования
  free_allocation                          BOOLEAN        NOT NULL DEFAULT FALSE, -- Свободное распределение
  free_allocation_reason                   TEXT, -- Причина свободного распределения
  voluntary_compensation                   BOOLEAN        NOT NULL DEFAULT TRUE, -- Добровольное возмещение
  voluntary_compensation_order_date        TIMESTAMPTZ(6), -- Дата приказа по добровольному возмещению
  voluntary_compensation_order_number      VARCHAR(50), -- Номер приказа по добровольному возмещению
  voluntary_compensation_confirmation_date TIMESTAMPTZ(6), -- Дата получения извещения о добровольном возмещение
  cort_order_date                          TIMESTAMPTZ(6), -- Возмещение через суд, Дата приказа
  cort_order_number                        VARCHAR(50), -- Возмещение через суд, Номер приказа
  archive                                  BOOLEAN                 DEFAULT FALSE -- Пометка о том, что запись находится в архиве
);

CREATE UNIQUE INDEX allocation_uuid_idx
  ON cld_test.allocation USING BTREE (uuid);

-- ----------------------------
-- TABLE preferences
-- ----------------------------
DROP TABLE IF EXISTS cld_test.preferences;
CREATE TABLE cld_test.preferences (
  id                         BIGSERIAL PRIMARY KEY,
  uuid                       UUID           NOT NULL,
  dt_update                  TIMESTAMPTZ(6) NOT NULL,
  notifications_enabled      BOOLEAN        NOT NULL DEFAULT TRUE, -- Включены/Выключены уведомления
  archive_enabled            BOOLEAN        NOT NULL DEFAULT TRUE, -- Включен/Выключен архив
  archive_term               INT            NOT NULL DEFAULT 30, -- Архивный период записей по отработки. После этого числа дней запись будет перемещена в архив
  confirmation_notice_term   INT            NOT NULL DEFAULT 3, -- За какое количество дней будет выдано уведомление о том, что у конкретного человека истекает подтверждение об отработке
  allocation_end_notice_term INT            NOT NULL DEFAULT 1 -- За какое количество дней до окончания срока отработки будет выданно данное сообщение
);

CREATE UNIQUE INDEX preferences_uuid_idx
  ON cld_test.preferences USING BTREE (uuid);

-- ----------------------------
-- TABLE notifications
-- ----------------------------
DROP TABLE IF EXISTS cld_test.notifications_log;
CREATE TABLE cld_test.notifications_log (
  id                BIGSERIAL PRIMARY KEY,
  uuid              UUID           NOT NULL,
  dt_update         TIMESTAMPTZ(6) NOT NULL,
  viewed            BOOLEAN        NOT NULL DEFAULT FALSE, -- Признак того что уведомление было прочитано
  notification_date TIMESTAMPTZ(6) NOT NULL, -- Когда уведомление было создано
  notification_type VARCHAR        NOT NULL DEFAULT 'ALLOCATION_END',
  payload           TEXT           NOT NULL -- Непосредственно текст уведомления
);

CREATE UNIQUE INDEX notifications_log_uuid_idx
  ON cld_test.notifications_log USING BTREE (uuid);

-- ----------------------------
-- Foreign Key structure for table cld_test.groups
-- ----------------------------
ALTER TABLE cld_test.groups
  ADD FOREIGN KEY (ruler_id) REFERENCES cld_test.rulers;

-- ----------------------------
-- Foreign Key structure for table cld_test.allocation
-- ----------------------------
ALTER TABLE cld_test.allocation
  ADD FOREIGN KEY (group_id) REFERENCES cld_test.groups;

ALTER TABLE cld_test.allocation
  ADD FOREIGN KEY (organisation_id) REFERENCES cld_test.organisations;

ALTER TABLE cld_test.allocation
  ADD FOREIGN KEY (student_id) REFERENCES cld_test.students;

ALTER TABLE cld_test.allocation
  ADD FOREIGN KEY (order_id) REFERENCES cld_test.orders;
