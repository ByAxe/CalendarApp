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
  issue_year     INTEGER        NOT NULL DEFAULT 2016, -- Год выпуска
  issue_month    INTEGER        NOT NULL DEFAULT 1,
  stage          VARCHAR        NOT NULL DEFAULT 'FIRST', -- Ступень образования
  ruler_id       BIGINT         NOT NULL
);

CREATE UNIQUE INDEX groups_uuid_idx
  ON cld.groups USING BTREE (uuid);

CREATE INDEX groups_issue_idx
  ON cld.groups USING BTREE (issue_year, issue_month);

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
  group_id    BIGINT         NOT NULL,
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
  rank       INT DEFAULT 1, -- Разряд
  starts     TIMESTAMPTZ(6) NOT NULL, -- Дата приказа
  number     VARCHAR(50)    NOT NULL, -- Номер приказа
  payload    TEXT
);

CREATE UNIQUE INDEX orders_uuid_idx
  ON cld.orders USING BTREE (uuid);

-- ----------------------------
-- TABLE allocation
-- ----------------------------
DROP TABLE IF EXISTS cld.allocation;
CREATE TABLE cld.allocation (
  id                                       BIGSERIAL PRIMARY KEY,
  uuid                                     UUID           NOT NULL,
  dt_update                                TIMESTAMPTZ(6) NOT NULL,
  parent_id                                BIGINT,
  organisation_id                          BIGINT         NOT NULL,
  student_id                               BIGINT         NOT NULL,
  order_id                                 BIGINT,
  confirmations                            VARCHAR ARRAY, -- Подтверждения об отработке
  army                                     BOOLEAN        NOT NULL DEFAULT FALSE,
  free_allocation                          BOOLEAN        NOT NULL DEFAULT FALSE, -- Свободное распределение
  free_allocation_reason                   TEXT, -- Причина свободного распределения
  compensation_type                        VARCHAR        NOT NULL DEFAULT 'NONE', -- Тип возмещения
  compensation_order_date                  TIMESTAMPTZ(6), -- Дата приказа по добровольному возмещению
  compensation_order_number                VARCHAR(50), -- Номер приказа по добровольному возмещению
  voluntary_compensation_confirmation_date TIMESTAMPTZ(6), -- Дата получения извещения о добровольном возмещении
  archive                                  BOOLEAN                 DEFAULT FALSE -- Пометка о том, что запись находится в архиве
);

CREATE UNIQUE INDEX allocation_uuid_idx
  ON cld.allocation USING BTREE (uuid);

-- ----------------------------
-- TABLE preferences
-- ----------------------------
DROP TABLE IF EXISTS cld.preferences;
CREATE TABLE cld.preferences (
  id                         BIGSERIAL PRIMARY KEY,
  uuid                       UUID           NOT NULL,
  dt_update                  TIMESTAMPTZ(6) NOT NULL,
  notifications_enabled      BOOLEAN        NOT NULL DEFAULT TRUE, -- Включены/Выключены уведомления
  archive_enabled            BOOLEAN        NOT NULL DEFAULT TRUE, -- Включен/Выключен архив
  archive_term               INT            NOT NULL DEFAULT 30, -- Архивный период записей по отработки. После этого числа дней запись будет перемещена в архив
  confirmation_notice_term   INT            NOT NULL DEFAULT 3, -- За какое количество дней будет выдано уведомление о том, что у конкретного человека истекает подтверждение об отработке
  allocation_end_notice_term INT            NOT NULL DEFAULT 1 -- За какое количество дней до окончания срока отработки будет выдано данное сообщение
);

CREATE UNIQUE INDEX preferences_uuid_idx
  ON cld.preferences USING BTREE (uuid);

-- ----------------------------
-- TABLE notifications
-- ----------------------------
DROP TABLE IF EXISTS cld.notifications_log;
CREATE TABLE cld.notifications_log (
  id                BIGSERIAL PRIMARY KEY,
  uuid              UUID           NOT NULL,
  dt_update         TIMESTAMPTZ(6) NOT NULL,
  viewed            BOOLEAN        NOT NULL DEFAULT FALSE, -- Признак того что уведомление было прочитано
  notification_date TIMESTAMPTZ(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(2), -- Когда уведомление было создано
  notification_type VARCHAR        NOT NULL DEFAULT 'ALLOCATION_END',
  payload           TEXT           NOT NULL -- Непосредственно текст уведомления
);

CREATE UNIQUE INDEX notifications_log_uuid_idx
  ON cld.notifications_log USING BTREE (uuid);

-- ----------------------------
-- Foreign Key structure for table cld.students
-- ----------------------------
ALTER TABLE cld.students
  ADD FOREIGN KEY (group_id) REFERENCES cld.groups;

-- ----------------------------
-- Foreign Key structure for table cld.groups
-- ----------------------------
ALTER TABLE cld.groups
  ADD FOREIGN KEY (ruler_id) REFERENCES cld.rulers;

-- ----------------------------
-- Foreign Key structure for table cld.allocation
-- ----------------------------

ALTER TABLE cld.allocation
  ADD FOREIGN KEY (organisation_id) REFERENCES cld.organisations;

ALTER TABLE cld.allocation
  ADD FOREIGN KEY (student_id) REFERENCES cld.students;

ALTER TABLE cld.allocation
  ADD FOREIGN KEY (order_id) REFERENCES cld.orders;

-- ----------------------------
-- QUARTZ tables
-- ----------------------------
-- Thanks to Patrick Lightbody for submitting this...
--
-- In your Quartz properties file, you'll need to set
-- org.quartz.jobStore.driverDelegateClass = org.quartz.impl.jdbcjobstore.PostgreSQLDelegate
SET search_path TO cld;
DROP TABLE IF EXISTS qrtz_fired_triggers;
DROP TABLE IF EXISTS QRTZ_PAUSED_TRIGGER_GRPS;
DROP TABLE IF EXISTS QRTZ_SCHEDULER_STATE;
DROP TABLE IF EXISTS QRTZ_LOCKS;
DROP TABLE IF EXISTS qrtz_simple_triggers;
DROP TABLE IF EXISTS qrtz_cron_triggers;
DROP TABLE IF EXISTS qrtz_simprop_triggers;
DROP TABLE IF EXISTS QRTZ_BLOB_TRIGGERS;
DROP TABLE IF EXISTS qrtz_triggers;
DROP TABLE IF EXISTS qrtz_job_details;
DROP TABLE IF EXISTS qrtz_calendars;

CREATE TABLE qrtz_job_details
(
  SCHED_NAME        VARCHAR(120) NOT NULL,
  JOB_NAME          VARCHAR(200) NOT NULL,
  JOB_GROUP         VARCHAR(200) NOT NULL,
  DESCRIPTION       VARCHAR(250) NULL,
  JOB_CLASS_NAME    VARCHAR(250) NOT NULL,
  IS_DURABLE        BOOL         NOT NULL,
  IS_NONCONCURRENT  BOOL         NOT NULL,
  IS_UPDATE_DATA    BOOL         NOT NULL,
  REQUESTS_RECOVERY BOOL         NOT NULL,
  JOB_DATA          BYTEA        NULL,
  PRIMARY KEY (SCHED_NAME, JOB_NAME, JOB_GROUP)
);

CREATE TABLE qrtz_triggers
(
  SCHED_NAME     VARCHAR(120) NOT NULL,
  TRIGGER_NAME   VARCHAR(200) NOT NULL,
  TRIGGER_GROUP  VARCHAR(200) NOT NULL,
  JOB_NAME       VARCHAR(200) NOT NULL,
  JOB_GROUP      VARCHAR(200) NOT NULL,
  DESCRIPTION    VARCHAR(250) NULL,
  NEXT_FIRE_TIME BIGINT       NULL,
  PREV_FIRE_TIME BIGINT       NULL,
  PRIORITY       INTEGER      NULL,
  TRIGGER_STATE  VARCHAR(16)  NOT NULL,
  TRIGGER_TYPE   VARCHAR(8)   NOT NULL,
  START_TIME     BIGINT       NOT NULL,
  END_TIME       BIGINT       NULL,
  CALENDAR_NAME  VARCHAR(200) NULL,
  MISFIRE_INSTR  SMALLINT     NULL,
  JOB_DATA       BYTEA        NULL,
  PRIMARY KEY (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP),
  FOREIGN KEY (SCHED_NAME, JOB_NAME, JOB_GROUP)
  REFERENCES QRTZ_JOB_DETAILS (SCHED_NAME, JOB_NAME, JOB_GROUP)
);

CREATE TABLE qrtz_simple_triggers
(
  SCHED_NAME      VARCHAR(120) NOT NULL,
  TRIGGER_NAME    VARCHAR(200) NOT NULL,
  TRIGGER_GROUP   VARCHAR(200) NOT NULL,
  REPEAT_COUNT    BIGINT       NOT NULL,
  REPEAT_INTERVAL BIGINT       NOT NULL,
  TIMES_TRIGGERED BIGINT       NOT NULL,
  PRIMARY KEY (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP),
  FOREIGN KEY (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP)
  REFERENCES QRTZ_TRIGGERS (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP)
);

CREATE TABLE qrtz_cron_triggers
(
  SCHED_NAME      VARCHAR(120) NOT NULL,
  TRIGGER_NAME    VARCHAR(200) NOT NULL,
  TRIGGER_GROUP   VARCHAR(200) NOT NULL,
  CRON_EXPRESSION VARCHAR(120) NOT NULL,
  TIME_ZONE_ID    VARCHAR(80),
  PRIMARY KEY (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP),
  FOREIGN KEY (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP)
  REFERENCES QRTZ_TRIGGERS (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP)
);

CREATE TABLE qrtz_simprop_triggers
(
  SCHED_NAME    VARCHAR(120)   NOT NULL,
  TRIGGER_NAME  VARCHAR(200)   NOT NULL,
  TRIGGER_GROUP VARCHAR(200)   NOT NULL,
  STR_PROP_1    VARCHAR(512)   NULL,
  STR_PROP_2    VARCHAR(512)   NULL,
  STR_PROP_3    VARCHAR(512)   NULL,
  INT_PROP_1    INT            NULL,
  INT_PROP_2    INT            NULL,
  LONG_PROP_1   BIGINT         NULL,
  LONG_PROP_2   BIGINT         NULL,
  DEC_PROP_1    NUMERIC(13, 4) NULL,
  DEC_PROP_2    NUMERIC(13, 4) NULL,
  BOOL_PROP_1   BOOL           NULL,
  BOOL_PROP_2   BOOL           NULL,
  PRIMARY KEY (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP),
  FOREIGN KEY (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP)
  REFERENCES QRTZ_TRIGGERS (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP)
);

CREATE TABLE qrtz_blob_triggers
(
  SCHED_NAME    VARCHAR(120) NOT NULL,
  TRIGGER_NAME  VARCHAR(200) NOT NULL,
  TRIGGER_GROUP VARCHAR(200) NOT NULL,
  BLOB_DATA     BYTEA        NULL,
  PRIMARY KEY (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP),
  FOREIGN KEY (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP)
  REFERENCES QRTZ_TRIGGERS (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP)
);

CREATE TABLE qrtz_calendars
(
  SCHED_NAME    VARCHAR(120) NOT NULL,
  CALENDAR_NAME VARCHAR(200) NOT NULL,
  CALENDAR      BYTEA        NOT NULL,
  PRIMARY KEY (SCHED_NAME, CALENDAR_NAME)
);


CREATE TABLE qrtz_paused_trigger_grps
(
  SCHED_NAME    VARCHAR(120) NOT NULL,
  TRIGGER_GROUP VARCHAR(200) NOT NULL,
  PRIMARY KEY (SCHED_NAME, TRIGGER_GROUP)
);

CREATE TABLE qrtz_fired_triggers
(
  SCHED_NAME        VARCHAR(120) NOT NULL,
  ENTRY_ID          VARCHAR(95)  NOT NULL,
  TRIGGER_NAME      VARCHAR(200) NOT NULL,
  TRIGGER_GROUP     VARCHAR(200) NOT NULL,
  INSTANCE_NAME     VARCHAR(200) NOT NULL,
  FIRED_TIME        BIGINT       NOT NULL,
  SCHED_TIME        BIGINT       NOT NULL,
  PRIORITY          INTEGER      NOT NULL,
  STATE             VARCHAR(16)  NOT NULL,
  JOB_NAME          VARCHAR(200) NULL,
  JOB_GROUP         VARCHAR(200) NULL,
  IS_NONCONCURRENT  BOOL         NULL,
  REQUESTS_RECOVERY BOOL         NULL,
  PRIMARY KEY (SCHED_NAME, ENTRY_ID)
);

CREATE TABLE qrtz_scheduler_state
(
  SCHED_NAME        VARCHAR(120) NOT NULL,
  INSTANCE_NAME     VARCHAR(200) NOT NULL,
  LAST_CHECKIN_TIME BIGINT       NOT NULL,
  CHECKIN_INTERVAL  BIGINT       NOT NULL,
  PRIMARY KEY (SCHED_NAME, INSTANCE_NAME)
);

CREATE TABLE qrtz_locks
(
  SCHED_NAME VARCHAR(120) NOT NULL,
  LOCK_NAME  VARCHAR(40)  NOT NULL,
  PRIMARY KEY (SCHED_NAME, LOCK_NAME)
);

CREATE INDEX idx_qrtz_j_req_recovery
  ON qrtz_job_details (SCHED_NAME, REQUESTS_RECOVERY);
CREATE INDEX idx_qrtz_j_grp
  ON qrtz_job_details (SCHED_NAME, JOB_GROUP);

CREATE INDEX idx_qrtz_t_j
  ON qrtz_triggers (SCHED_NAME, JOB_NAME, JOB_GROUP);
CREATE INDEX idx_qrtz_t_jg
  ON qrtz_triggers (SCHED_NAME, JOB_GROUP);
CREATE INDEX idx_qrtz_t_c
  ON qrtz_triggers (SCHED_NAME, CALENDAR_NAME);
CREATE INDEX idx_qrtz_t_g
  ON qrtz_triggers (SCHED_NAME, TRIGGER_GROUP);
CREATE INDEX idx_qrtz_t_state
  ON qrtz_triggers (SCHED_NAME, TRIGGER_STATE);
CREATE INDEX idx_qrtz_t_n_state
  ON qrtz_triggers (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP, TRIGGER_STATE);
CREATE INDEX idx_qrtz_t_n_g_state
  ON qrtz_triggers (SCHED_NAME, TRIGGER_GROUP, TRIGGER_STATE);
CREATE INDEX idx_qrtz_t_next_fire_time
  ON qrtz_triggers (SCHED_NAME, NEXT_FIRE_TIME);
CREATE INDEX idx_qrtz_t_nft_st
  ON qrtz_triggers (SCHED_NAME, TRIGGER_STATE, NEXT_FIRE_TIME);
CREATE INDEX idx_qrtz_t_nft_misfire
  ON qrtz_triggers (SCHED_NAME, MISFIRE_INSTR, NEXT_FIRE_TIME);
CREATE INDEX idx_qrtz_t_nft_st_misfire
  ON qrtz_triggers (SCHED_NAME, MISFIRE_INSTR, NEXT_FIRE_TIME, TRIGGER_STATE);
CREATE INDEX idx_qrtz_t_nft_st_misfire_grp
  ON qrtz_triggers (SCHED_NAME, MISFIRE_INSTR, NEXT_FIRE_TIME, TRIGGER_GROUP, TRIGGER_STATE);

CREATE INDEX idx_qrtz_ft_trig_inst_name
  ON qrtz_fired_triggers (SCHED_NAME, INSTANCE_NAME);
CREATE INDEX idx_qrtz_ft_inst_job_req_rcvry
  ON qrtz_fired_triggers (SCHED_NAME, INSTANCE_NAME, REQUESTS_RECOVERY);
CREATE INDEX idx_qrtz_ft_j_g
  ON qrtz_fired_triggers (SCHED_NAME, JOB_NAME, JOB_GROUP);
CREATE INDEX idx_qrtz_ft_jg
  ON qrtz_fired_triggers (SCHED_NAME, JOB_GROUP);
CREATE INDEX idx_qrtz_ft_t_g
  ON qrtz_fired_triggers (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP);
CREATE INDEX idx_qrtz_ft_tg
  ON qrtz_fired_triggers (SCHED_NAME, TRIGGER_GROUP);

INSERT INTO cld.preferences (uuid, dt_update) VALUES ('092f11c7-c8c1-49c4-8dfe-a8e557886d11', CURRENT_TIMESTAMP(2));
\q