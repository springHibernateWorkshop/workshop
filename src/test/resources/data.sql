INSERT INTO CATEGORY_TAB (CATEGORY_ID, NAME, VERSION) VALUES (100, 'Category1', CURRENT_TIMESTAMP);
INSERT INTO CATEGORY_TAB (CATEGORY_ID, NAME, VERSION) VALUES (200, 'Category2', CURRENT_TIMESTAMP);
INSERT INTO CATEGORY_TAB (CATEGORY_ID, NAME, VERSION) VALUES (300, 'Category3', CURRENT_TIMESTAMP);
INSERT INTO CATEGORY_TAB (CATEGORY_ID, NAME, VERSION) VALUES (400, 'Category4', CURRENT_TIMESTAMP);

INSERT INTO SHOP_TAB (SHOP_ID, NAME, ADDRESS, VERSION) VALUES (100, 'shop_1', 'address_1', CURRENT_TIMESTAMP);
INSERT INTO SHOP_TAB (SHOP_ID, NAME, ADDRESS, VERSION) VALUES (200, 'shop_2', 'address_2', CURRENT_TIMESTAMP);
INSERT INTO SHOP_TAB (SHOP_ID, NAME, ADDRESS, VERSION) VALUES (300, 'shop_3', 'address_3', CURRENT_TIMESTAMP);
INSERT INTO SHOP_TAB (SHOP_ID, NAME, ADDRESS, VERSION) VALUES (400, 'shop_4', 'address_4', CURRENT_TIMESTAMP);

INSERT INTO ROLE_TAB (ROLE_ID, NAME) VALUES (1, 'ROLE_EMPLOYEE');
INSERT INTO ROLE_TAB (ROLE_ID, NAME) VALUES (2, 'ROLE_SUPERIOR');
INSERT INTO ROLE_TAB (ROLE_ID, NAME) VALUES (3, 'ROLE_ACCOUNTANT');
INSERT INTO ROLE_TAB (ROLE_ID, NAME) VALUES (4, 'ROLE_ADMINISTRATOR');

INSERT INTO RIGHT_TAB (RIGHT_ID, NAME) VALUES (1, 'VIEW_EXPENSES');
INSERT INTO RIGHT_TAB (RIGHT_ID, NAME) VALUES (2, 'CREATE_EXPENSES');
INSERT INTO RIGHT_TAB (RIGHT_ID, NAME) VALUES (3, 'DELETE_EXPENSES');
INSERT INTO RIGHT_TAB (RIGHT_ID, NAME) VALUES (4, 'EDIT_EXPENSES');
INSERT INTO RIGHT_TAB (RIGHT_ID, NAME) VALUES (5, 'SUBMIT_EXPENSES');
INSERT INTO RIGHT_TAB (RIGHT_ID, NAME) VALUES (6, 'APPROVE_REJECT_EXPENSES');
INSERT INTO RIGHT_TAB (RIGHT_ID, NAME) VALUES (7, 'VIEW_REPORTS');
INSERT INTO RIGHT_TAB (RIGHT_ID, NAME) VALUES (8, 'CREATE_USERS');
INSERT INTO RIGHT_TAB (RIGHT_ID, NAME) VALUES (9, 'DELETE_USERS');
INSERT INTO RIGHT_TAB (RIGHT_ID, NAME) VALUES (10, 'REASSIGN_EMPLOYEES');
INSERT INTO RIGHT_TAB (RIGHT_ID, NAME) VALUES (11, 'VIEW_SHOPS');
INSERT INTO RIGHT_TAB (RIGHT_ID, NAME) VALUES (12, 'CREATE_SHOPS');
INSERT INTO RIGHT_TAB (RIGHT_ID, NAME) VALUES (13, 'DELETE_SHOPS');
INSERT INTO RIGHT_TAB (RIGHT_ID, NAME) VALUES (14, 'EDIT_SHOPS');
INSERT INTO RIGHT_TAB (RIGHT_ID, NAME) VALUES (15, 'VIEW_CATEGORIES');
INSERT INTO RIGHT_TAB (RIGHT_ID, NAME) VALUES (16, 'CREATE_CATEGORIES');
INSERT INTO RIGHT_TAB (RIGHT_ID, NAME) VALUES (17, 'DELETE_CATEGORIES');
INSERT INTO RIGHT_TAB (RIGHT_ID, NAME) VALUES (18, 'EDIT_CATEGORIES');
INSERT INTO RIGHT_TAB (RIGHT_ID, NAME) VALUES (19, 'ROLE_EMPLOYEE');
INSERT INTO RIGHT_TAB (RIGHT_ID, NAME) VALUES (20, 'ROLE_SUPERIOR');
INSERT INTO RIGHT_TAB (RIGHT_ID, NAME) VALUES (21, 'ROLE_ACCOUNTANT');
INSERT INTO RIGHT_TAB (RIGHT_ID, NAME) VALUES (22, 'ROLE_ADMINISTRATOR');

INSERT INTO PERMISSION_TAB (ROLE_ID, RIGHT_ID) VALUES (1, 1);
INSERT INTO PERMISSION_TAB (ROLE_ID, RIGHT_ID) VALUES (1, 2);
INSERT INTO PERMISSION_TAB (ROLE_ID, RIGHT_ID) VALUES (1, 3);
INSERT INTO PERMISSION_TAB (ROLE_ID, RIGHT_ID) VALUES (1, 4);
INSERT INTO PERMISSION_TAB (ROLE_ID, RIGHT_ID) VALUES (1, 5);
INSERT INTO PERMISSION_TAB (ROLE_ID, RIGHT_ID) VALUES (1, 19);
INSERT INTO PERMISSION_TAB (ROLE_ID, RIGHT_ID) VALUES (2, 1);
INSERT INTO PERMISSION_TAB (ROLE_ID, RIGHT_ID) VALUES (2, 6);
INSERT INTO PERMISSION_TAB (ROLE_ID, RIGHT_ID) VALUES (2, 7);
INSERT INTO PERMISSION_TAB (ROLE_ID, RIGHT_ID) VALUES (2, 20);
INSERT INTO PERMISSION_TAB (ROLE_ID, RIGHT_ID) VALUES (3, 7);
INSERT INTO PERMISSION_TAB (ROLE_ID, RIGHT_ID) VALUES (3, 21);
INSERT INTO PERMISSION_TAB (ROLE_ID, RIGHT_ID) VALUES (4, 8);
INSERT INTO PERMISSION_TAB (ROLE_ID, RIGHT_ID) VALUES (4, 9);
INSERT INTO PERMISSION_TAB (ROLE_ID, RIGHT_ID) VALUES (4, 10);
INSERT INTO PERMISSION_TAB (ROLE_ID, RIGHT_ID) VALUES (4, 11);
INSERT INTO PERMISSION_TAB (ROLE_ID, RIGHT_ID) VALUES (4, 12);
INSERT INTO PERMISSION_TAB (ROLE_ID, RIGHT_ID) VALUES (4, 13);
INSERT INTO PERMISSION_TAB (ROLE_ID, RIGHT_ID) VALUES (4, 14);
INSERT INTO PERMISSION_TAB (ROLE_ID, RIGHT_ID) VALUES (4, 15);
INSERT INTO PERMISSION_TAB (ROLE_ID, RIGHT_ID) VALUES (4, 16);
INSERT INTO PERMISSION_TAB (ROLE_ID, RIGHT_ID) VALUES (4, 17);
INSERT INTO PERMISSION_TAB (ROLE_ID, RIGHT_ID) VALUES (4, 18);
INSERT INTO PERMISSION_TAB (ROLE_ID, RIGHT_ID) VALUES (4, 22);

INSERT INTO USER_TAB (USER_ID, USERNAME, PASSWORD, ROLE_ID, VERSION) VALUES (100, 'victoria','{bcrypt}$2a$10$Z.y5qu2v2/LIuzsDaHLEsOReksoMQmNC1CVasO9rUopRkRlUgtGc6', 1, CURRENT_TIMESTAMP);
INSERT INTO USER_TAB (USER_ID, USERNAME, PASSWORD, ROLE_ID, VERSION) VALUES (200, 'bartosz','{bcrypt}$2a$10$Z.y5qu2v2/LIuzsDaHLEsOReksoMQmNC1CVasO9rUopRkRlUgtGc6', 2, CURRENT_TIMESTAMP);
INSERT INTO USER_TAB (USER_ID, USERNAME, PASSWORD, ROLE_ID, VERSION) VALUES (300, 'kasia','{bcrypt}$2a$10$Z.y5qu2v2/LIuzsDaHLEsOReksoMQmNC1CVasO9rUopRkRlUgtGc6', 1, CURRENT_TIMESTAMP);
INSERT INTO USER_TAB (USER_ID, USERNAME, PASSWORD, ROLE_ID, VERSION) VALUES (400, 'manhton','{bcrypt}$2a$10$Z.y5qu2v2/LIuzsDaHLEsOReksoMQmNC1CVasO9rUopRkRlUgtGc6', 4, CURRENT_TIMESTAMP);
INSERT INTO USER_TAB (USER_ID, USERNAME, PASSWORD, ROLE_ID, VERSION) VALUES (500, 'raffael','{bcrypt}$2a$10$Z.y5qu2v2/LIuzsDaHLEsOReksoMQmNC1CVasO9rUopRkRlUgtGc6', 3, CURRENT_TIMESTAMP);
INSERT INTO USER_TAB (USER_ID, USERNAME, PASSWORD, ROLE_ID, VERSION) VALUES (600, 'krzysztof','{bcrypt}$2a$10$Z.y5qu2v2/LIuzsDaHLEsOReksoMQmNC1CVasO9rUopRkRlUgtGc6', 1, CURRENT_TIMESTAMP);
INSERT INTO USER_TAB (USER_ID, USERNAME, PASSWORD, ROLE_ID, VERSION) VALUES (700, 'michalina','{bcrypt}$2a$10$Z.y5qu2v2/LIuzsDaHLEsOReksoMQmNC1CVasO9rUopRkRlUgtGc6', 2, CURRENT_TIMESTAMP);

INSERT INTO SUPERIOR_TAB (SUPERIOR_ID, NAME, USER_ID, VERSION) VALUES (100, 'Michalina', 700, CURRENT_TIMESTAMP);
INSERT INTO SUPERIOR_TAB (SUPERIOR_ID, NAME, USER_ID, VERSION) VALUES (200, 'Bartosz', 200, CURRENT_TIMESTAMP);
INSERT INTO SUPERIOR_TAB (SUPERIOR_ID, NAME, USER_ID, VERSION) VALUES (300, 'Agnieszka', NULL, CURRENT_TIMESTAMP);

INSERT INTO EMPLOYEE_TAB (EMPLOYEE_ID, NAME, USER_ID, SUPERIOR_ID, VERSION) VALUES (100, 'Victoria', 100, 200, CURRENT_TIMESTAMP);
INSERT INTO EMPLOYEE_TAB (EMPLOYEE_ID, NAME, USER_ID, SUPERIOR_ID, VERSION) VALUES (200, 'Krzysztof', 600, 100, CURRENT_TIMESTAMP);
INSERT INTO EMPLOYEE_TAB (EMPLOYEE_ID, NAME, USER_ID, SUPERIOR_ID, VERSION) VALUES (300, 'Kasia', 300, 200, CURRENT_TIMESTAMP);

INSERT INTO EXPENSE_TAB (EXPENSE_ID, NAME, TOTAL, EXPENSE_DATE, CATEGORY_ID, SHOP_ID, EMPLOYEE_ID, STATUS, NOTE, VERSION) VALUES (100, 'expense_1', 0.99, TO_DATE('2023-03-01', 'YYYY-MM-DD'), 100, 100, 100, 'INITIAL', 'Note 1', CURRENT_TIMESTAMP);
INSERT INTO EXPENSE_TAB (EXPENSE_ID, NAME, TOTAL, EXPENSE_DATE, CATEGORY_ID, SHOP_ID, EMPLOYEE_ID, STATUS, NOTE, VERSION) VALUES (200, 'expense_2', 9.05, TO_DATE('2024-02-01', 'YYYY-MM-DD'), 200, 200, 100, 'PENDING', 'Note 2', CURRENT_TIMESTAMP);
INSERT INTO EXPENSE_TAB (EXPENSE_ID, NAME, TOTAL, EXPENSE_DATE, CATEGORY_ID, SHOP_ID, EMPLOYEE_ID, STATUS, NOTE, VERSION) VALUES (300, 'expense_3', 22.0, TO_DATE('2023-05-01', 'YYYY-MM-DD'), 200, 200, 300, 'INITIAL', 'Note 3', CURRENT_TIMESTAMP);
INSERT INTO EXPENSE_TAB (EXPENSE_ID, NAME, TOTAL, EXPENSE_DATE, CATEGORY_ID, SHOP_ID, EMPLOYEE_ID, STATUS, NOTE, VERSION) VALUES (400, 'expense_4', 13.0, TO_DATE('2024-02-01', 'YYYY-MM-DD'), 200, 200, 100, 'PENDING', 'Note 4', CURRENT_TIMESTAMP);
INSERT INTO EXPENSE_TAB (EXPENSE_ID, NAME, TOTAL, EXPENSE_DATE, CATEGORY_ID, SHOP_ID, EMPLOYEE_ID, STATUS, NOTE, VERSION) VALUES (500, 'expense_5', 13.0, TO_DATE('2024-05-01', 'YYYY-MM-DD'), 200, 200, 300, 'INITIAL', 'Note 5', CURRENT_TIMESTAMP);
INSERT INTO EXPENSE_TAB (EXPENSE_ID, NAME, TOTAL, EXPENSE_DATE, CATEGORY_ID, SHOP_ID, EMPLOYEE_ID, STATUS, NOTE, VERSION) VALUES (600, 'expense_6', 17.0, TO_DATE('2020-04-01', 'YYYY-MM-DD'), 300, 300, 200, 'INITIAL', 'Note 6', CURRENT_TIMESTAMP);


