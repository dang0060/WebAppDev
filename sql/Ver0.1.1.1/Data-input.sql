INSERT INTO mydb.groups
(name, description) VALUES
('Powerball','card game'),
('DragonRiders','table top RPG'),
('The Dudes','FPS shooter'),
('Happy Feet','DDR party'),
('Dungeon Raiders','WOW raid party'),
('The Sith Lords','Star Wars battlefront');

INSERT INTO mydb.users
(username,password,email) VALUES
('Homer','password','springField1@nowhere.com'),
('Bart','guessit','springField2@nowhere.com'),
('Marge','homie','springField3@nowhere.com'),
('Maggie', 'ohmnom','springField3@nowhere.com'),
('Lisa','saxphone','springField4@nowhere.com'),
('Soap', 'SSDD','lockandload@sas.com'),
('Price','cigar','fireatwill@sas.com'),
('Luke','theforce','skywalker2@jediplace.com'),
('Yoda','usetheforceyoumust','themaster@jediplace.com'),
('House','thedocisin','elementry@hospital.com'),
('Bill','iluvwindows','therichest@ms.com'),
('Android','appleistasty','everywhere@allplace.com');

INSERT INTO mydb.users_groups
(user_id,group_id,is_leader)VALUES
('1','6','1'), /*Homer is the leader for group 6*/
('2','3','1'), /*Bart is the leader for group 3*/
('3','1','1'), /*Marge is the leader for group 1*/
('4','4','1'), /*Maggie is the leader for group 4*/
('5','5','1'), /*Lisa is the leader for group 5*/
('6','3','0'), /*Soap is a member of group 3*/
('7','2','1'), /*Price is the leader for group 2*/
('8','6','0'), /*Luke is a member of group 6*/
('9','4','0'), /*Yoda is a member of group 4*/
('10','2','0'), /*House is a member of group2*/
('11','1','0'), /*Bill is a member of group1*/
('12','5','0'); /*Android is a member of group5*/