--1. Создать таблицу отдел(department), колонки: id, name, isProfit(приносит прибыль - да/нет)
CREATE TABLE department (id SERIAL PRIMARY KEY, name VARCHAR(100), isProfit BOOLEAN);

--2. Вставить туда:
--Бухгалтерия, нет
--Кредитный отдел, да
--Отдел продаж, да
--Правление, нет
INSERT INTO department (name, isProfit) VALUES ('Бухгалтерия', false);
INSERT INTO department (name, isProfit) VALUES ('Кредитный отдел', true);
INSERT INTO department (name, isProfit) VALUES ('Отдел продаж', true);
INSERT INTO department (name, isProfit) VALUES ('Правление', false);

--3. Создать таблицу работник(employee), колонки: id, full_name (фио), salary, department_id (связь с отделом)
CREATE TABLE employee (id SERIAL PRIMARY KEY, full_name VARCHAR(100), salary INT, 
					   department_id INT REFERENCES department(id));
--4. Вставить в таблицу работник:
--Петров Иван, 30000, отдел продаж(подумать, какое значение нужно вставлять)
--Иванова Наталья, 50000, Бухгалтерия
--Мирских Петр, 100000, Правление
--Улюкаев Владимир, 200000, Правление
--Заморский Виктор, 70000, Кредитный Отдел
INSERT INTO employee (full_name, salary, department_id) VALUES ('Петров Иван', 30000, 3);
INSERT INTO employee (full_name, salary, department_id) VALUES ('Иванова Наталья', 50000, 1);
INSERT INTO employee (full_name, salary, department_id) VALUES ('Мирских Петр', 100000, 4);
INSERT INTO employee (full_name, salary, department_id) VALUES ('Улюкаев Владимир', 200000, 4);
INSERT INTO employee (full_name, salary, department_id) VALUES ('Заморский Виктор', 70000, 2);

--5. Вывести на экран всех сотрудников Правления - вывод: все поля employee
SELECT * FROM employee
WHERE department_id = 4;

--6. Вывести на экран сумму всех зарплат сотрудников - вывод: общая сумма всех зарплат
SELECT SUM(salary)
FROM employee;

--7. Вывести на экран фио сотрудника, является ли он сотрудником прибыльного отдела - вывод: фио, принадлежит к прибыльному отделу
SELECT e.full_name, d.isProfit
FROM employee AS e INNER JOIN department AS d ON e.department_id = d.id;

--8. Вывести на экран только тех сотрудников, которые получают от 10_000 до 100_000 (включительно) - вывод: все поля employee
SELECT * FROM employee
WHERE salary > 10000 AND salary <= 100000;

--9. Удалить Мирского Петра из таблицы 
DELETE FROM employee
WHERE full_name = 'Мирских Петр';

--10. Поменять название Кредитного отдела на депозитный и поменять у него статус "приносит прибыль" на нет
UPDATE department SET name = 'Депозитный отдел', isProfit = false
WHERE name = 'Кредитный отдел';

--11. Вывести всех сотрудников содержащих "иван" (в независимости от регистра) - вывод: все поля employee
SELECT * FROM employee
WHERE full_name ILIKE '%иван';

--12. Вывести среднюю зарплату по отделам - вывод: отдел, средняя зарплата
SELECT d.name, AVG(e.salary)
FROM department AS d INNER JOIN employee AS e ON d.id = e.department_id
GROUP BY d.name;

--13.Удалить таблицы employee, department
DROP TABLE employee;
DROP TABLE department;