-- phpMyAdmin SQL Dump
-- version 4.8.3
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: 2019 年 5 月 24 日 03:13
-- サーバのバージョン： 10.1.37-MariaDB
-- PHP Version: 7.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `LAA1055594-nds`
--

-- --------------------------------------------------------

--
-- テーブルの構造 `all_user_answers`
--

CREATE TABLE `all_user_answers` (
  `all_user_answer_id` int(11) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  `question_id` int(11) DEFAULT NULL,
  `answer_choice` int(11) DEFAULT NULL,
  `answer_time` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- テーブルの構造 `answers_db`
--

CREATE TABLE `answers_db` (
  `question_id` int(11) NOT NULL,
  `answer_number` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- テーブルの構造 `answers_rate_db`
--

CREATE TABLE `answers_rate_db` (
  `question_id` int(11) NOT NULL,
  `answer_rate` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- テーブルの構造 `exams_db`
--

CREATE TABLE `exams_db` (
  `exam_id` int(11) NOT NULL,
  `exam_name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- テーブルの構造 `exams_numbers_db`
--

CREATE TABLE `exams_numbers_db` (
  `exam_id` int(11) NOT NULL,
  `exams_number` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- テーブルの構造 `exams_questions_db`
--

CREATE TABLE `exams_questions_db` (
  `exams_id` int(11) NOT NULL,
  `exams_number` varchar(255) NOT NULL,
  `question_id` int(11) NOT NULL,
  `question_number` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- テーブルの構造 `genres_db`
--

CREATE TABLE `genres_db` (
  `genre_id` int(11) NOT NULL,
  `genre_name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- テーブルの構造 `image_db`
--

CREATE TABLE `image_db` (
  `question_id` int(11) NOT NULL,
  `file_name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- テーブルの構造 `questions_db`
--

CREATE TABLE `questions_db` (
  `question_id` int(11) NOT NULL,
  `question` text,
  `is_have_image` tinyint(1) DEFAULT NULL,
  `comment` text NOT NULL,
  `update_date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- テーブルの構造 `questions_genres_db`
--

CREATE TABLE `questions_genres_db` (
  `question_id` int(11) NOT NULL,
  `genre_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- テーブルの構造 `users`
--

CREATE TABLE `users` (
  `user_id` int(11) NOT NULL,
  `token` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `all_user_answers`
--
ALTER TABLE `all_user_answers`
  ADD PRIMARY KEY (`all_user_answer_id`),
  ADD KEY `all_user_answers_answers_db_question_id_answer_number_fk` (`question_id`,`all_user_answer_id`),
  ADD KEY `all_user_answers_users_user_id_fk` (`user_id`);

--
-- Indexes for table `answers_db`
--
ALTER TABLE `answers_db`
  ADD PRIMARY KEY (`question_id`,`answer_number`);

--
-- Indexes for table `answers_rate_db`
--
ALTER TABLE `answers_rate_db`
  ADD PRIMARY KEY (`question_id`);

--
-- Indexes for table `exams_db`
--
ALTER TABLE `exams_db`
  ADD PRIMARY KEY (`exam_id`);

--
-- Indexes for table `exams_numbers_db`
--
ALTER TABLE `exams_numbers_db`
  ADD PRIMARY KEY (`exam_id`,`exams_number`);

--
-- Indexes for table `exams_questions_db`
--
ALTER TABLE `exams_questions_db`
  ADD PRIMARY KEY (`exams_id`,`exams_number`,`question_id`,`question_number`),
  ADD KEY `exams_questions_db_questions_db_question_id_fk` (`question_id`);

--
-- Indexes for table `genres_db`
--
ALTER TABLE `genres_db`
  ADD PRIMARY KEY (`genre_id`);

--
-- Indexes for table `image_db`
--
ALTER TABLE `image_db`
  ADD PRIMARY KEY (`question_id`);

--
-- Indexes for table `questions_db`
--
ALTER TABLE `questions_db`
  ADD PRIMARY KEY (`question_id`);

--
-- Indexes for table `questions_genres_db`
--
ALTER TABLE `questions_genres_db`
  ADD PRIMARY KEY (`question_id`,`genre_id`),
  ADD KEY `questions_genres_db_genres_db_genre_id_fk` (`genre_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`user_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `answers_rate_db`
--
ALTER TABLE `answers_rate_db`
  MODIFY `question_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- ダンプしたテーブルの制約
--

--
-- テーブルの制約 `all_user_answers`
--
ALTER TABLE `all_user_answers`
  ADD CONSTRAINT `all_user_answers_answers_db_question_id_answer_number_fk` FOREIGN KEY (`question_id`,`all_user_answer_id`) REFERENCES `answers_db` (`question_id`, `answer_number`),
  ADD CONSTRAINT `all_user_answers_users_user_id_fk` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`);

--
-- テーブルの制約 `answers_db`
--
ALTER TABLE `answers_db`
  ADD CONSTRAINT `answers_db_questions_db_question_id_fk` FOREIGN KEY (`question_id`) REFERENCES `questions_db` (`question_id`);

--
-- テーブルの制約 `answers_rate_db`
--
ALTER TABLE `answers_rate_db`
  ADD CONSTRAINT `answers_rate_db_questions_db_question_id_fk` FOREIGN KEY (`question_id`) REFERENCES `questions_db` (`question_id`);

--
-- テーブルの制約 `exams_numbers_db`
--
ALTER TABLE `exams_numbers_db`
  ADD CONSTRAINT `exams_numbers_db_exams_db_exam_id_fk` FOREIGN KEY (`exam_id`) REFERENCES `exams_db` (`exam_id`);

--
-- テーブルの制約 `exams_questions_db`
--
ALTER TABLE `exams_questions_db`
  ADD CONSTRAINT `exams_questions_db_exams_numbers_db_exam_id_exams_number_fk` FOREIGN KEY (`exams_id`,`exams_number`) REFERENCES `exams_numbers_db` (`exam_id`, `exams_number`),
  ADD CONSTRAINT `exams_questions_db_questions_db_question_id_fk` FOREIGN KEY (`question_id`) REFERENCES `questions_db` (`question_id`);

--
-- テーブルの制約 `image_db`
--
ALTER TABLE `image_db`
  ADD CONSTRAINT `image_db_questions_db_question_id_fk` FOREIGN KEY (`question_id`) REFERENCES `questions_db` (`question_id`);

--
-- テーブルの制約 `questions_genres_db`
--
ALTER TABLE `questions_genres_db`
  ADD CONSTRAINT `questions_genres_db_genres_db_genre_id_fk` FOREIGN KEY (`genre_id`) REFERENCES `genres_db` (`genre_id`),
  ADD CONSTRAINT `questions_genres_db_questions_db_question_id_fk` FOREIGN KEY (`question_id`) REFERENCES `questions_db` (`question_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
