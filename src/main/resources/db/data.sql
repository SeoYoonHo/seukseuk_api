-- 직원 데이터 삽입
INSERT INTO employee (email, name, age, gender, tel)
VALUES
    ('하뭉.email', '이하윤', 30, 'Male', '123-456-7890'),
    ('서연.eamil', '송서현', 25, 'Female', '123-456-7891'),
    ('누구.email', '정윤서', 28, 'Male', '123-456-7892');

-- 이하윤의 작업 요일과 일당 설정 (예: Saturday에 120000원)
INSERT INTO employee_working_day_of_weeks (employee_id, day_of_week, wage)
VALUES
    ((SELECT employee_id FROM employee WHERE name = '이하윤'), 'SATURDAY', 100000);

-- 송서현의 작업 요일과 일당 설정 (예: Monday에 100000원, Tuesday에 100000원)
INSERT INTO employee_working_day_of_weeks (employee_id, day_of_week, wage)
VALUES
    ((SELECT employee_id FROM employee WHERE name = '송서현'), 'MONDAY', 120000),
    ((SELECT employee_id FROM employee WHERE name = '송서현'), 'TUESDAY', 104000);

-- 정윤서의 작업 요일과 일당 설정 (예: Friday에 134000원)
INSERT INTO employee_working_day_of_weeks (employee_id, day_of_week, wage)
VALUES
    ((SELECT employee_id FROM employee WHERE name = '정윤서'), 'FRIDAY', 134000);
