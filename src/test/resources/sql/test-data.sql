-- УВЕДОМЛЕНИЯ --
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

-- УПРАВЛЯЮЩИЕ --
INSERT INTO cld.rulers (uuid, dt_update, last_name, first_name, middle_name, payload) VALUES
  ('9b405c7a-be79-4c7b-9314-dfdbe0177d83', CURRENT_TIMESTAMP(2), 'Пчёлкин', 'Михаил', 'Константинович',
   '8 (925) 249-85-22; Дата рождения - 27 апреля 1987 года');
INSERT INTO cld.rulers (uuid, dt_update, last_name, first_name, middle_name, payload) VALUES
  ('c767cbff-06be-4c7a-807d-7bcf4632337d', CURRENT_TIMESTAMP(2), 'Морозова', 'Станислава', 'Олеговна',
   '8 (958) 944-75-92; Дата рождения - 13 ноября 1968 года; Любимый цвет - Зелёный');
INSERT INTO cld.rulers (uuid, dt_update, last_name, first_name, middle_name, payload) VALUES
  ('60833b32-b75a-44ce-8a59-918e29aadf8f', CURRENT_TIMESTAMP(2), 'Егорова', 'Наталья', 'Яковлевна',
   '8 (963) 436-90-25; Дата рождения - 18 марта 1965 года; Любимый цвет - Жёлтый');

-- ОРГАНИЗАЦИИ --
INSERT INTO cld.organisations (uuid, dt_update, title, address, telephone, contacts) VALUES
  ('808493b6-4745-46a0-bad9-521e71c12c68', CURRENT_TIMESTAMP(2), 'Рога и Копыта', 'ул. Великих Предпринимателей 27к1',
   '8 (034) 455-55-55', 'email: rogaIKopita@gmail.com');
INSERT INTO cld.organisations (uuid, dt_update, title, address, telephone, contacts) VALUES
  ('57408e4b-6b52-42d6-9022-1ea9618714c7', CURRENT_TIMESTAMP(2), 'KPAN', 'ул. Программистов 14',
   '8 (034) 666-66-66', 'email: kpan@kpan.by');

-- ГРУППЫ --
INSERT INTO cld.groups (uuid, dt_update, title, specialization, qualification, description, number, hours, ruler_id, issue_year, issue_month, stage)
VALUES
  ('1d5caa06-6fe9-46b7-92a3-6360ba867ac8', CURRENT_TIMESTAMP(2), 'Техники-сантехники', 'Экономическая деятельность',
                                           'Технология разработки сантехнического обеспечения',
                                           'Техники сантехники. Такая группа когда то была.', '143ТС', 120,
                                           1, 2019, 9, 'FIRST');
INSERT INTO cld.groups (uuid, dt_update, title, specialization, qualification, description, number, hours, ruler_id, issue_year, issue_month, stage)
VALUES
  ('1655a2ad-e7b8-47a0-a17a-949dd1627493', CURRENT_TIMESTAMP(2), 'Мехатроники', 'Изобретение приборов',
                                           'Технология разработки изобретения механизмов',
                                           'Техники приборостроители. Такая группа когда то была.', '101ИП',
                                           333, 2, 2019, 9, 'FIRST');
INSERT INTO cld.groups (uuid, dt_update, title, specialization, qualification, description, number, hours, ruler_id, issue_year, issue_month, stage)
VALUES
  ('f9da9b76-6ed1-4199-ad3b-eefedaaf8f84', CURRENT_TIMESTAMP(2), 'Космонавты', 'Полеты на марс',
                                           'Технология прибытия в иные миры',
                                           'Техники исследовтели. Такая группа когда то была.', '007МИ',
                                           10000, 3, 2019, 9, 'SECOND');

-- ПРИКАЗЫ --
INSERT INTO cld.orders (uuid, dt_update, profession, starts, number, payload) VALUES
  ('f1ce86ae-89db-4cb9-b96e-8c7cab4c3317', CURRENT_TIMESTAMP(2), 'Инженер программист', CURRENT_TIMESTAMP(2),
   '2223345hg', 'Принятие на работу');

-- ВЫПУСКНИКИ --
INSERT INTO cld.students (uuid, dt_update, last_name, middle_name, first_name, telephone, address, group_id) VALUES
  ('29a57449-a0f9-46ba-b903-357267b360fc', CURRENT_TIMESTAMP(2), 'Арсеньев', 'Ферапонт', 'Денисович',
   '8 (936) 628-63-32',
   '187341, г. Пограничный, ул. Вагонников 3-я, дом 63, квартира 283', 1);
INSERT INTO cld.students (uuid, dt_update, last_name, middle_name, first_name, telephone, address, group_id) VALUES
  ('57349ae5-5bc2-4fb6-8082-a37c5f8f044a', CURRENT_TIMESTAMP(2), 'Вихирева', 'Лилия', 'Викторовна',
   '8 (906) 584-32-68',
   '197762, г. Павловск, ул. Герцена, дом 21, квартира 234', 2);
INSERT INTO cld.students (uuid, dt_update, last_name, middle_name, first_name, telephone, address, group_id) VALUES
  ('7af5a85f-8512-4fcc-9355-532a78a3935d', CURRENT_TIMESTAMP(2), 'Андронова', 'Лариса', 'Владиславовна',
   '8 (935) 273-13-22',
   '150515, г. Грязи, ул. Академическая, дом 55, квартира 53', 1);
INSERT INTO cld.students (uuid, dt_update, last_name, middle_name, first_name, telephone, address, group_id) VALUES
  ('53cef394-6484-48e5-93f4-4e53a54ab9ab', CURRENT_TIMESTAMP(2), 'Сидоров', 'Боголюб', 'Вениаминович',
   '8 (920) 503-61-20',
   '393388, г. Нехаевский, ул. Авиаконструктора Миля, дом 25, квартира 46', 1);
INSERT INTO cld.students (uuid, dt_update, last_name, middle_name, first_name, telephone, address, group_id) VALUES
  ('b1f9964f-4b6b-4e7c-b80b-a173ae592e5a', CURRENT_TIMESTAMP(2), 'Фролов', 'Исай', 'Сергеевич',
   '8 (965) 907-49-55',
   '368262, г. Плюсса, ул. Барклая, дом 71, квартира 190', 3);

-- РАСПРЕДЕЛЕНИЕ --
INSERT INTO cld.allocation (uuid, dt_update, organisation_id, student_id, order_id, confirmations)
VALUES ('7ee16dd5-5b51-42a8-994b-b3da13b7f9f6', current_timestamp(2), 1, 1, 1,
        ARRAY ['', '', '', '', '', '', '', '', '', '']);
INSERT INTO cld.allocation (uuid, dt_update, organisation_id, student_id, order_id, confirmations)
VALUES ('6ca78147-1a30-497c-8d15-6fd0d35e1137', current_timestamp(2), 1, 2, 1,
        ARRAY ['Работает', '', '', '', '', '', '', '', '', '']);
INSERT INTO cld.allocation (uuid, dt_update, organisation_id, student_id, order_id, confirmations, archive)
VALUES ('e22dbe92-0169-4bc2-86dc-9c76590b21e3', current_timestamp(2), 2, 3, 1,
        ARRAY ['Работает', 'Работает', 'Работает', 'Работает', 'Работает', 'Работает', 'Работает', 'Работает', 'Работает', 'Работает'],
        TRUE);
INSERT INTO cld.allocation (uuid, dt_update, organisation_id, student_id, order_id, confirmations)
VALUES ('50d22dec-4d7f-43d9-84d2-c0bd15b29981', current_timestamp(2), 2, 5, 1,
        ARRAY ['', '', '', '', '', '', '', '', '', '']);