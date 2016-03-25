INSERT INTO mydb.groups
(groupname, description,address,latitude,longitude) VALUES
('Powerball','card game','1385 Woodroffe Ave, Ottawa, ON K2G 1V8', 45.349394, -75.758364),
('DragonRiders','table top RPG', '228 Bank St, Ottawa, ON K2P 1X1',45.4168546,-75.6978038),
('The Dudes','FPS shooter','75 Laurier Ave E, Ottawa, ON K1N 6N5',45.423433, -75.685458),
('Happy Feet','DDR party','1077 Bank St, Ottawa, ON K1S 3W9',45.3945333, -75.6834339),
('Dungeon Raiders','WOW raid party','223 Bank St, Ottawa, ON K2P 1W9',45.4169935,-75.6977367),
('The Sith Lords','Star Wars battlefront','1125 Colonel By Dr, Ottawa, ON K1S 5B6',45.383750,-75.697496);

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
('1','3','0'),
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

INSERT INTO mydb.conversation (user_start_id, user_recieve_id, status) VALUES
(1, 3, 'ACTIVE');

INSERT INTO mydb.conversation_message (message, time_stamp, user_id_fk, conversation_id_fk) VALUES
('Marge, where are you?', NOW(), 1, 1),
('Right here Homie, on the website', NOW(), 3, 1),
('But I cant see you', NOW(), 1, 1),
('Of course you cant were on the internet', NOW(), 3, 1),
('This internet stuff is hard', NOW(), 1, 1),
('No, youre just dumb Homer', NOW(), 3, 1);

