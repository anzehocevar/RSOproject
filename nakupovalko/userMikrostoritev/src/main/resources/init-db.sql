CREATE TABLE user IF NOT EXISTS (
                      id INT AUTO_INCREMENT PRIMARY KEY,
                      name VARCHAR(100) NOT NULL,
                      surname VARCHAR(100) NOT NULL,
                      username VARCHAR(100) NOT NULL,
                      email VARCHAR(100) UNIQUE NOT NULL

);

INSERT IGNORE INTO 'user' (name, surname, username, email)
VALUES ('Anze', 'Hočevar', 'anzeh', 'anze.hocevar@gmail.com'),
       ('Veronika', 'Matek', 'vm0012', 'vm0012@student.uni-lj.si')
       ('Micka', 'Novak', 'user', 'micka.novak@hotmail.si');
