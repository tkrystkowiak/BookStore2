insert into book (id, title, authors, status) values (1, 'First book', 'Jan Kowalski', 'FREE');
insert into book (id, title, authors, status) values (2, 'Second book', 'Zbigniew Nowak', 'FREE');
insert into book (id, title, authors, status) values (3, 'Third book', 'Janusz Jankowski', 'FREE');
insert into book (id, title, authors, status) values (4, 'Starter kit book', 'Kacper Ossoliński', 'FREE');
insert into book (id, title, authors, status) values (5, 'Z kamerą wśród programistów', 'Krystyna Czubówna', 'MISSING');

insert into user (id, user_name, password) values (1, 'admin', '$2a$10$wi.3YnqdiDiLu6zaT5cw2uqg3bB5nDNg0jVr4AYKkFXgTygQl5UzC');
insert into user (id, user_name, password) values (2, 'user', '$2a$10$X.L54ItnrG7WNToINRmyW.9YC8D7zzRibxeGvkd7NC8vskpbdWbpO');
insert into user (id, user_name, password) values (3, 'indiana', '$2a$10$38PPKiA1Vsdey4SPyApwier41dbIKWNDpkXwp.ggmgZUwy8eSSJxC');

insert into user_role (id, user_name, role) values (1, 'admin', 'ROLE_ADMIN');
insert into user_role (id, user_name, role) values (2, 'user', 'ROLE_USER');
insert into user_role (id, user_name, role) values (3, 'indiana', 'ROLE_USER');
