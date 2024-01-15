INSERT INTO USER_TAB (USER_ID, NAME) VALUES (100, 'Victoria');
INSERT INTO USER_TAB (USER_ID, NAME) VALUES (200, 'Bartosz');
INSERT INTO USER_TAB (USER_ID, NAME) VALUES (300, 'Kasia');
INSERT INTO USER_TAB (USER_ID, NAME) VALUES (400, 'Manh Ton');
INSERT INTO USER_TAB (USER_ID, NAME) VALUES (500, 'Raffael');


INSERT INTO CATEGORY_TAB (CATEGORY_ID, NAME) VALUES (100, 'Category1');
INSERT INTO CATEGORY_TAB (CATEGORY_ID, NAME) VALUES (200, 'Category2');
INSERT INTO CATEGORY_TAB (CATEGORY_ID, NAME) VALUES (300, 'Category3');

INSERT INTO SHOP_TAB (SHOP_ID, NAME, ADDRESS) VALUES (100, 'shop_1', 'address_1');
INSERT INTO SHOP_TAB (SHOP_ID, NAME, ADDRESS) VALUES (200, 'shop_2', 'address_2');
INSERT INTO SHOP_TAB (SHOP_ID, NAME, ADDRESS) VALUES (300, 'shop_3', 'address_3');

INSERT INTO EXPENSE_TAB (EXPENSE_ID, TOTAL, EXPENSE_DATE, CATEGORY_ID, SHOP_ID, USER_ID, NOTE) VALUES (1, 0.99, '11-11-2011', 100, 200, 5);
INSERT INTO EXPENSE_TAB (EXPENSE_ID, TOTAL, EXPENSE_DATE, CATEGORY_ID, SHOP_ID, USER_ID, NOTE) VALUES (2, 9.05, '23-02-2022', 300, 300, 3);
INSERT INTO EXPENSE_TAB (EXPENSE_ID, TOTAL, EXPENSE_DATE, CATEGORY_ID, SHOP_ID, USER_ID, NOTE) VALUES (3, 22.0, '05-12-2023', 100, 200, 4);