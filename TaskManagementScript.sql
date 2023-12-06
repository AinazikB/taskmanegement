CREATE TABLE task
(
   task_id VARCHAR(100) DEFAULT 'T' || nextval('task_id_sequence') PRIMARY KEY,
   title VARCHAR(50) NOT NULL,
   task_type VARCHAR(100),
   description VARCHAR(150),
   priority VARCHAR(100),
   deadline DATE
);

DELETE FROM task;

CREATE SEQUENCE task_id_sequence;

INSERT INTO task (title, task_type, description, priority, deadline) VALUES ('TAskManager', 'Homework Task', 'Write a program', 'LOW', '2023-12-23');
INSERT INTO task (title, task_type, description, priority, deadline) VALUES ('ToDoApp', 'Shopping Task', 'Buy something', 'MEDIUM', '2023-12-25');

select  * from task;