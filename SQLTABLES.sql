CREATE DATABASE DemoDataBase;
USE DemoDataBase;
create table users (Num int primary key not null auto_increment,
FullName varchar(50) not null,
UserID int not null,
EmailAddress varchar(50) not null,
HomeAddress varchar(50) not null ,
PhoneNumber int not null,
GroupID int not null,
Title varchar(50) not null);

insert into users (FullName, UserID, EmailAddress, PhoneNumber, GroupID, Title)
values ('Eden Daddo', 2198219878,'edenDado@gmail.com,','Haifa, Remot Remez, Haviva Reich 54', 0547823641,  1928312093, 'User'),
('Karen Yakov', 2127726318,'karenYakov@gmail.com,','Haifa, Remot Alon, Dovnov 18', 0547374388,  1928312093, 'User'),
('Leen Yakov', 1823718982,'leenYakov@gmail.com,','Haifa, Remot Alon, Dovnov 16', 0518723618,  1928312093, 'User'),
('Rami Benet', 2338291782,'ramiBenet@gmail.com,','Haifa, Remot Remez, Haviva Reich 60', 0534289782,  1928312093, 'User'),
('Abo Majd', 1212323982,'aboMajd@gmail.com,','Haifa, Remot Remez, Haviva Reich 40', 0541271872,  1928312093, 'User'),
('Yonatan Boris', 1728631726,'yonatanBoris@gmail.com,','Haifa, Neve Shanan, Netiv Hen 12', 0541782631,  1928312093, 'User'),
('Louis Litt', 3237298379,'louisLitt@gmail.com,','Haifa, Neve Shanan, Netiv Hen 10', 0576718722,  1928312093, 'User'),
('Yohanan Bloomfield', 3213798179,'yohananBloomfield@gmail.com,','Haifa, Neve Shanan, Hanita 24', 0542882281,  1928312093, 'Manager');

create table tasks (Num int primary key not null auto_increment,
TaskID int not null,
TaskDescription varchar(50) not null,
UserName varchar(50) not null,
UserID int not null,
State varchar(50),
SubmissionTime timestamp default current_timestamp,
Volunteer varchar(50) not null);

insert into tasks(TaskID, TaskDescription, UserName, UserID, State, SubmissionTime, Volunteer)
values (1829371289,'Walk the pets', 'Eden Daddo',2198219878, 'Pending', '2024-02-15 10:00:00', 'none'),
(1829371284,'Buy medical equipment', 'Leen Yakov',1823718982, 'Pending', '2024-02-16 12:00:00', 'none'),
(1829371288,'Help buy groceries', 'Leen Yakov',1823718982, 'Pending', '2024-02-16 12:00:05', 'none'),
(1829371287,'Clean the house', 'Karen Yakov',2127726318, 'Pending', '2024-02-17 09:00:00', 'none'),
(1829371286,'Take care of the children', 'Karen Yakov',2127726318, 'Pending', '2024-02-17 10:00:00', 'none'),
(1829371285,'Give a ride', 'Eden daddo',2198219878, 'Pending', '2024-02-18 11:00:00', 'none');