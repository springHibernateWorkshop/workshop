INSERT INTO USER_TAB (USER_ID, USERNAME, PASSWORD, ROLE_ID) VALUES (100, 'usr1', 'pass1', 2);
INSERT INTO USER_TAB (USER_ID, USERNAME, PASSWORD, ROLE_ID) VALUES (200, 'usr2', 'pass2', 1);
INSERT INTO USER_TAB (USER_ID, USERNAME, PASSWORD, ROLE_ID) VALUES (300, 'usr3', 'pass3', 2);
INSERT INTO USER_TAB (USER_ID, USERNAME, PASSWORD, ROLE_ID) VALUES (400, 'usr4', 'pass4', 2);
INSERT INTO USER_TAB (USER_ID, USERNAME, PASSWORD, ROLE_ID) VALUES (500, 'usr5', 'pass5', 1);


INSERT INTO CATEGORY_TAB (CATEGORY_ID, NAME) VALUES (100, 'Category1');
INSERT INTO CATEGORY_TAB (CATEGORY_ID, NAME) VALUES (200, 'Category2');
INSERT INTO CATEGORY_TAB (CATEGORY_ID, NAME) VALUES (300, 'Category3');

INSERT INTO SHOP_TAB (SHOP_ID, NAME, ADDRESS) VALUES (100, 'shop_1', 'address_1');
INSERT INTO SHOP_TAB (SHOP_ID, NAME, ADDRESS) VALUES (200, 'shop_2', 'address_2');
INSERT INTO SHOP_TAB (SHOP_ID, NAME, ADDRESS) VALUES (300, 'shop_3', 'address_3');

INSERT INTO EXPENSE_TAB (EXPENSE_ID, TOTAL, EXPENSE_DATE, CATEGORY_ID, SHOP_ID, USER_ID, NOTE) VALUES (100, 0.99, CURRENT_DATE, 100, 100, 500, 'Note 1');
INSERT INTO EXPENSE_TAB (EXPENSE_ID, TOTAL, EXPENSE_DATE, CATEGORY_ID, SHOP_ID, USER_ID, NOTE) VALUES (200, 9.05, CURRENT_DATE, 200, 200, 300, 'Note 2');
INSERT INTO EXPENSE_TAB (EXPENSE_ID, TOTAL, EXPENSE_DATE, CATEGORY_ID, SHOP_ID, USER_ID, NOTE) VALUES (300, 22.0, CURRENT_DATE, 200, 200, 400, 'Note 3');