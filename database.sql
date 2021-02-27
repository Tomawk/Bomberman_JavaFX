CREATE DATABASE  IF NOT EXISTS `bomberman`;
USE `bomberman`;

DROP TABLE IF EXISTS `leaderboard`;
CREATE TABLE `leaderboard` (
  `id` varchar(45) NOT NULL,
  `nickname` varchar(40) NOT NULL,
  `score` int(11) NOT NULL,
  `difficolta` varchar(40) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


LOCK TABLES `leaderboard` WRITE;

INSERT INTO `leaderboard` VALUES ('1ec33738-550e-4096-85cc-ec35c81bb159','Tommy',24400,'DIFFICILE'),('33e861e2-18c5-44db-81f6-5961d20c07f8','Tommy',20200,'FACILE'),('e61cfde8-e081-4d1a-93c3-14af31fd8c6b','Tommy',30800,'DIFFICILE'),('e8e83f55-f038-4085-abc5-d1ab09801701','Tommy',30200,'DIFFICILE'),('ed7ceeb4-f6f7-4278-98fa-7ac7373a361c','Carlo',19600,'FACILE');

UNLOCK TABLES;

