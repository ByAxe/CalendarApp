INSERT INTO calendar_test.cld_test.events (uuid, dt_update, starts, ends, title)
VALUES ('7dcda3c5-f93e-4fce-9573-cd5dd01c4819', CURRENT_TIMESTAMP(2), '2001-12-23 14:39:53.66+03',
        '2101-12-23 14:39:53.66+03', 'Новое Событие!');

INSERT INTO calendar_test.cld_test.events (uuid, dt_update, starts, ends, title)
VALUES ('c9e68eb8-9002-4628-a443-b910c128d035', CURRENT_TIMESTAMP(2), '2001-12-23 14:39:53.66+03',
        '2101-12-23 14:39:53.66+03', 'Новое Событие [2]!');

INSERT INTO cld.notifications_log (uuid, dt_update, payload) VALUES
  ('46da5a45-8bfe-4418-a583-c5b2c48122d5', CURRENT_TIMESTAMP(2),
   'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.');
INSERT INTO cld.notifications_log (uuid, dt_update, payload) VALUES
  ('d3782406-7098-4f55-89e4-aad10fcce18d', CURRENT_TIMESTAMP(2),
   'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.');
INSERT INTO cld.notifications_log (uuid, dt_update, payload) VALUES
  ('e1d2c315-9a83-41aa-bb01-932b8ee21121', CURRENT_TIMESTAMP(2),
   'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.');
INSERT INTO cld.notifications_log (uuid, dt_update, payload) VALUES
  ('2d9a3b23-3df4-41ea-b513-1b99b9a5236e', CURRENT_TIMESTAMP(2),
   'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.');
INSERT INTO cld.notifications_log (uuid, dt_update, payload) VALUES
  ('55da6eaa-5f44-4f4a-a824-ba219aa2674a', CURRENT_TIMESTAMP(2),
   'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.');
INSERT INTO cld.notifications_log (uuid, dt_update, payload) VALUES
  ('f370a3d8-7959-49f0-895e-5797d275c5be', CURRENT_TIMESTAMP(2),
   'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.!');
INSERT INTO cld.notifications_log (uuid, dt_update, payload) VALUES
  ('922f85b5-034c-42c9-8893-c3194b089fca', CURRENT_TIMESTAMP(2),
   'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.');