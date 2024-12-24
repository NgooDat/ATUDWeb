-- T?o LOGIN và USER cho Admin
CREATE LOGIN admin_rule WITH PASSWORD = 'Fl0r3nt!n@';
USE shop_c;
CREATE USER admin_rule FOR LOGIN admin_rule;

-- T?o LOGIN và USER cho Staff
CREATE LOGIN staff_rule WITH PASSWORD = '@hB3qu0!O';
USE shop_c;
CREATE USER staff_rule FOR LOGIN staff_rule;

-- T?o LOGIN và USER cho Customer
CREATE LOGIN customer_rule WITH PASSWORD = 'D0@ntu34yN7gd!';
USE shop_c;
CREATE USER customer_rule FOR LOGIN customer_rule;

-- T?o LOGIN và USER cho Visitor
CREATE LOGIN visitor_rule WITH PASSWORD = '1Nt3rte!1aC0u^c!1';
USE shop_c;
CREATE USER visitor_rule FOR LOGIN visitor_rule;

-- Gán quy?n Admin
ALTER ROLE db_owner ADD MEMBER admin_rule;

--staff
-- Read, Update, Create cho b?ng s?n ph?m và liên quan
GRANT SELECT, INSERT, UPDATE ON products TO staff_rule;
GRANT SELECT, INSERT, UPDATE ON product_details TO staff_rule;
GRANT SELECT, INSERT, UPDATE ON sizes TO staff_rule;
GRANT SELECT, INSERT, UPDATE ON brands TO staff_rule;
GRANT SELECT, INSERT, UPDATE ON types TO staff_rule;
GRANT SELECT, INSERT, UPDATE ON materials TO staff_rule;
GRANT SELECT, INSERT, UPDATE ON origins TO staff_rule;
GRANT SELECT, INSERT, UPDATE ON orders TO staff_rule;
GRANT SELECT, INSERT, UPDATE ON order_details TO staff_rule;
GRANT SELECT, INSERT, UPDATE ON order_status TO staff_rule;

-- Read cho customers, feedbacks
GRANT SELECT ON customers TO staff_rule;
GRANT SELECT ON feedbacks TO staff_rule;

-- Read & Update cho staff, accounts
GRANT SELECT, UPDATE ON staffs TO staff_rule;
GRANT SELECT, UPDATE ON accounts TO staff_rule;
GRANT SELECT ON rules TO staff_rule;

--customer
-- Read cho s?n ph?m và liên quan
GRANT SELECT ON products TO customer_rule;
GRANT SELECT ON product_details TO customer_rule;
GRANT SELECT ON sizes TO customer_rule;
GRANT SELECT ON brands TO customer_rule;
GRANT SELECT ON types TO customer_rule;
GRANT SELECT ON materials TO customer_rule;
GRANT SELECT ON origins TO customer_rule;

-- Read, Create, Update cho orders, carts, feedbacks
GRANT SELECT, INSERT, UPDATE ON orders TO customer_rule;
GRANT SELECT, INSERT, UPDATE ON carts TO customer_rule;
GRANT SELECT, INSERT, UPDATE ON feedbacks TO customer_rule;

-- Read & Update cho customers, accounts
GRANT SELECT, UPDATE ON customers TO customer_rule;
GRANT SELECT, UPDATE ON accounts TO customer_rule;
GRANT SELECT ON rules TO customer_rule;

--visitor
-- Read cho s?n ph?m và liên quan
GRANT SELECT ON products TO visitor_rule;
GRANT SELECT ON product_details TO visitor_rule;
GRANT SELECT ON sizes TO visitor_rule;
GRANT SELECT ON brands TO visitor_rule;
GRANT SELECT ON types TO visitor_rule;
GRANT SELECT ON materials TO visitor_rule;
GRANT SELECT ON origins TO visitor_rule;

-- Create cho accounts, customers
GRANT INSERT ON accounts TO visitor_rule;
GRANT INSERT ON customers TO visitor_rule;
GRANT SELECT ON accounts TO visitor_rule;
GRANT SELECT ON rules TO visitor_rule;