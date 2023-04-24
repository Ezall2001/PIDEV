<?php

declare(strict_types=1);

namespace DoctrineMigrations;

use Doctrine\DBAL\Schema\Schema;
use Doctrine\Migrations\AbstractMigration;

/**
 * Auto-generated Migration: Please modify to your needs!
 */
final class Version20230417234634 extends AbstractMigration
{
    public function getDescription(): string
    {
        return '';
    }

    public function up(Schema $schema): void
    {
        // this up() migration is auto-generated, please modify it to your needs
        $this->addSql('CREATE TABLE messenger_messages (id BIGINT AUTO_INCREMENT NOT NULL, body LONGTEXT NOT NULL, headers LONGTEXT NOT NULL, queue_name VARCHAR(190) NOT NULL, created_at DATETIME NOT NULL, available_at DATETIME NOT NULL, delivered_at DATETIME DEFAULT NULL, INDEX IDX_75EA56E0FB7336F0 (queue_name), INDEX IDX_75EA56E0E3BD61CE (available_at), INDEX IDX_75EA56E016BA31DB (delivered_at), PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_unicode_ci` ENGINE = InnoDB');
        $this->addSql('ALTER TABLE user_sessions DROP FOREIGN KEY user_session_user_foreign_key');
        $this->addSql('DROP TABLE user_sessions');
        $this->addSql('ALTER TABLE answers DROP FOREIGN KEY answer_user_foreign_key');
        $this->addSql('ALTER TABLE answers DROP FOREIGN KEY answer_question_foreign_key');
        $this->addSql('ALTER TABLE answers CHANGE message message VARCHAR(150) DEFAULT NULL, CHANGE vote_nb vote_nb INT NOT NULL, CHANGE created_at created_at DATETIME NOT NULL');
        $this->addSql('ALTER TABLE answers ADD CONSTRAINT FK_50D0C606A76ED395 FOREIGN KEY (user_id) REFERENCES users (id)');
        $this->addSql('ALTER TABLE answers ADD CONSTRAINT FK_50D0C6061E27F6BF FOREIGN KEY (question_id) REFERENCES questions (id)');
        $this->addSql('ALTER TABLE answers RENAME INDEX answer_user_foreign_key TO IDX_50D0C606A76ED395');
        $this->addSql('ALTER TABLE answers RENAME INDEX answer_question_foreign_key TO IDX_50D0C6061E27F6BF');
        $this->addSql('ALTER TABLE courses DROP FOREIGN KEY course_subject_foreign_key');
        $this->addSql('DROP INDEX fk_id_subject ON courses');
        $this->addSql('ALTER TABLE courses ADD $id_test INT DEFAULT NULL, ADD $idSubject INT DEFAULT NULL, DROP id_subject, CHANGE name name VARCHAR(500) NOT NULL, CHANGE difficulty difficulty VARCHAR(255) NOT NULL');
        $this->addSql('ALTER TABLE courses ADD CONSTRAINT FK_A9A55A4CA8428AFA FOREIGN KEY ($idSubject) REFERENCES subjects (id)');
        $this->addSql('ALTER TABLE courses ADD CONSTRAINT FK_A9A55A4CD62D0EB5 FOREIGN KEY ($id_test) REFERENCES tests (id)');
        $this->addSql('CREATE INDEX IDX_A9A55A4CA8428AFA ON courses ($idSubject)');
        $this->addSql('CREATE INDEX IDX_A9A55A4CD62D0EB5 ON courses ($id_test)');
        $this->addSql('ALTER TABLE participations DROP FOREIGN KEY participation_user_foreign_key');
        $this->addSql('ALTER TABLE participations DROP FOREIGN KEY participation_session_foreign_key');
        $this->addSql('ALTER TABLE participations CHANGE id_user id_user INT DEFAULT NULL, CHANGE id_session id_session INT DEFAULT NULL');
        $this->addSql('ALTER TABLE participations ADD CONSTRAINT FK_FDC6C6E86B3CA4B FOREIGN KEY (id_user) REFERENCES users (id)');
        $this->addSql('ALTER TABLE participations ADD CONSTRAINT FK_FDC6C6E8ED97CA4 FOREIGN KEY (id_session) REFERENCES sessions (id)');
        $this->addSql('ALTER TABLE participations RENAME INDEX participation_user_foreign_key TO IDX_FDC6C6E86B3CA4B');
        $this->addSql('ALTER TABLE participations RENAME INDEX participation_session_foreign_key TO IDX_FDC6C6E8ED97CA4');
        $this->addSql('ALTER TABLE questions DROP FOREIGN KEY question_subject_foreign_key');
        $this->addSql('ALTER TABLE questions DROP FOREIGN KEY question_user_foreign_key');
        $this->addSql('ALTER TABLE questions CHANGE subject_id subject_id INT DEFAULT NULL, CHANGE user_id user_id INT DEFAULT NULL, CHANGE title title VARCHAR(2550) DEFAULT NULL, CHANGE description description VARCHAR(2550) DEFAULT NULL, CHANGE created_at created_at DATETIME NOT NULL');
        $this->addSql('ALTER TABLE questions ADD CONSTRAINT FK_8ADC54D523EDC87 FOREIGN KEY (subject_id) REFERENCES subjects (id)');
        $this->addSql('ALTER TABLE questions ADD CONSTRAINT FK_8ADC54D5A76ED395 FOREIGN KEY (user_id) REFERENCES users (id)');
        $this->addSql('ALTER TABLE questions RENAME INDEX fk_subject_id TO IDX_8ADC54D523EDC87');
        $this->addSql('ALTER TABLE questions RENAME INDEX fk_user_id TO IDX_8ADC54D5A76ED395');
        $this->addSql('ALTER TABLE resources DROP FOREIGN KEY resource_session_foreign_key');
        $this->addSql('DROP INDEX resource_session_foreign_key ON resources');
        $this->addSql('ALTER TABLE resources ADD session_id INT DEFAULT NULL, DROP id_session, CHANGE name name VARCHAR(250) NOT NULL, CHANGE file_path file_path VARCHAR(1000) NOT NULL');
        $this->addSql('ALTER TABLE resources ADD CONSTRAINT FK_EF66EBAE613FECDF FOREIGN KEY (session_id) REFERENCES sessions (id)');
        $this->addSql('CREATE INDEX IDX_EF66EBAE613FECDF ON resources (session_id)');
        $this->addSql('ALTER TABLE sessions DROP FOREIGN KEY session_course');
        $this->addSql('ALTER TABLE sessions DROP FOREIGN KEY session_user_foreign_key');
        $this->addSql('ALTER TABLE sessions CHANGE id_course id_course INT DEFAULT NULL, CHANGE id_user id_user INT DEFAULT NULL, CHANGE date date VARCHAR(255) NOT NULL, CHANGE start_time start_time VARCHAR(255) NOT NULL, CHANGE end_time end_time VARCHAR(255) NOT NULL, CHANGE places places INT NOT NULL, CHANGE meet_link meet_link VARCHAR(500) NOT NULL, CHANGE image_link image_link VARCHAR(500) NOT NULL');
        $this->addSql('ALTER TABLE sessions ADD CONSTRAINT FK_9A609D1330A9DA54 FOREIGN KEY (id_course) REFERENCES courses (id)');
        $this->addSql('ALTER TABLE sessions ADD CONSTRAINT FK_9A609D136B3CA4B FOREIGN KEY (id_user) REFERENCES users (id)');
        $this->addSql('ALTER TABLE sessions RENAME INDEX session_course TO IDX_9A609D1330A9DA54');
        $this->addSql('ALTER TABLE sessions RENAME INDEX session_user_foreign_key TO IDX_9A609D136B3CA4B');
        $this->addSql('DROP INDEX unique_sbject_name ON subjects');
        $this->addSql('ALTER TABLE subjects CHANGE classes_esprit classes_esprit VARCHAR(255) NOT NULL');
        $this->addSql('ALTER TABLE test_qs DROP FOREIGN KEY test_qs_test_foreign_key');
        $this->addSql('ALTER TABLE test_qs CHANGE optionA optiona VARCHAR(150) NOT NULL, CHANGE optionB optionb VARCHAR(150) NOT NULL, CHANGE optionC optionc VARCHAR(150) NOT NULL, CHANGE optionD optiond VARCHAR(150) NOT NULL, CHANGE correct_option correct_option VARCHAR(150) NOT NULL, CHANGE question question VARCHAR(150) NOT NULL');
        $this->addSql('ALTER TABLE test_qs ADD CONSTRAINT FK_1F9519C71E5D0459 FOREIGN KEY (test_id) REFERENCES tests (id)');
        $this->addSql('ALTER TABLE test_qs RENAME INDEX test_qs_test_foreign_key TO IDX_1F9519C71E5D0459');
        $this->addSql('ALTER TABLE test_results DROP FOREIGN KEY test_results_test_foreign_key');
        $this->addSql('ALTER TABLE test_results DROP FOREIGN KEY test_results_user_foreign_key');
        $this->addSql('DROP INDEX test_results_test_foreign_key ON test_results');
        $this->addSql('DROP INDEX test_results_user_foreign_key ON test_results');
        $this->addSql('ALTER TABLE test_results ADD test_id INT DEFAULT NULL, ADD user_id INT DEFAULT NULL, DROP id_user, DROP id_test');
        $this->addSql('ALTER TABLE test_results ADD CONSTRAINT FK_43E230DC1E5D0459 FOREIGN KEY (test_id) REFERENCES tests (id)');
        $this->addSql('ALTER TABLE test_results ADD CONSTRAINT FK_43E230DCA76ED395 FOREIGN KEY (user_id) REFERENCES users (id)');
        $this->addSql('CREATE UNIQUE INDEX UNIQ_43E230DC1E5D0459 ON test_results (test_id)');
        $this->addSql('CREATE UNIQUE INDEX UNIQ_43E230DCA76ED395 ON test_results (user_id)');
        $this->addSql('ALTER TABLE tests DROP FOREIGN KEY test_course_foreign_key');
        $this->addSql('ALTER TABLE tests DROP FOREIGN KEY test_subject_foreign_key');
        $this->addSql('DROP INDEX tests_id_subject_fk ON tests');
        $this->addSql('DROP INDEX tests_id_course_fk ON tests');
        $this->addSql('ALTER TABLE tests ADD subject_id INT NOT NULL, ADD course_id INT NOT NULL, ADD id_test INT NOT NULL, DROP id_subject, DROP id_course, CHANGE type type VARCHAR(150) NOT NULL');
        $this->addSql('ALTER TABLE tests ADD CONSTRAINT FK_1260FC5E23EDC87 FOREIGN KEY (subject_id) REFERENCES subjects (id)');
        $this->addSql('ALTER TABLE tests ADD CONSTRAINT FK_1260FC5E591CC992 FOREIGN KEY (course_id) REFERENCES courses (id)');
        $this->addSql('ALTER TABLE tests ADD CONSTRAINT FK_1260FC5E535F620E FOREIGN KEY (id_test) REFERENCES test_results (id)');
        $this->addSql('CREATE INDEX IDX_1260FC5E23EDC87 ON tests (subject_id)');
        $this->addSql('CREATE INDEX IDX_1260FC5E591CC992 ON tests (course_id)');
        $this->addSql('CREATE INDEX IDX_1260FC5E535F620E ON tests (id_test)');
        $this->addSql('ALTER TABLE users CHANGE bio bio VARCHAR(500) NOT NULL, CHANGE avatar_path avatar_path VARCHAR(300) NOT NULL, CHANGE score score INT NOT NULL, CHANGE type type VARCHAR(50) NOT NULL, CHANGE warnings warnings INT NOT NULL');
        $this->addSql('ALTER TABLE votes DROP FOREIGN KEY vote_answer_foreign_key');
        $this->addSql('ALTER TABLE votes DROP FOREIGN KEY vote_user_foreign_key');
        $this->addSql('ALTER TABLE votes CHANGE answer_id answer_id INT DEFAULT NULL, CHANGE vote_type vote_type INT NOT NULL');
        $this->addSql('ALTER TABLE votes ADD CONSTRAINT FK_518B7ACFAA334807 FOREIGN KEY (answer_id) REFERENCES answers (id)');
        $this->addSql('ALTER TABLE votes ADD CONSTRAINT FK_518B7ACF6B3CA4B FOREIGN KEY (id_user) REFERENCES users (id)');
        $this->addSql('ALTER TABLE votes RENAME INDEX vote_answer_foreign_key TO IDX_518B7ACFAA334807');
        $this->addSql('ALTER TABLE votes RENAME INDEX vote_user_foreign_key TO IDX_518B7ACF6B3CA4B');
    }

    public function down(Schema $schema): void
    {
        // this down() migration is auto-generated, please modify it to your needs
        $this->addSql('CREATE TABLE user_sessions (id INT AUTO_INCREMENT NOT NULL, id_user INT NOT NULL, created_at DATETIME DEFAULT \'current_timestamp()\' NOT NULL, expires_at DATETIME DEFAULT \'current_timestamp()\' NOT NULL, INDEX user_session_user_foreign_key (id_user), PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_general_ci` ENGINE = InnoDB COMMENT = \'\' ');
        $this->addSql('ALTER TABLE user_sessions ADD CONSTRAINT user_session_user_foreign_key FOREIGN KEY (id_user) REFERENCES users (id) ON UPDATE CASCADE ON DELETE CASCADE');
        $this->addSql('DROP TABLE messenger_messages');
        $this->addSql('ALTER TABLE answers DROP FOREIGN KEY FK_50D0C606A76ED395');
        $this->addSql('ALTER TABLE answers DROP FOREIGN KEY FK_50D0C6061E27F6BF');
        $this->addSql('ALTER TABLE answers CHANGE message message TEXT NOT NULL, CHANGE vote_nb vote_nb INT DEFAULT 0, CHANGE created_at created_at DATETIME DEFAULT \'current_timestamp()\' NOT NULL');
        $this->addSql('ALTER TABLE answers ADD CONSTRAINT answer_user_foreign_key FOREIGN KEY (user_id) REFERENCES users (id) ON UPDATE CASCADE ON DELETE CASCADE');
        $this->addSql('ALTER TABLE answers ADD CONSTRAINT answer_question_foreign_key FOREIGN KEY (question_id) REFERENCES questions (id) ON UPDATE CASCADE ON DELETE CASCADE');
        $this->addSql('ALTER TABLE answers RENAME INDEX idx_50d0c6061e27f6bf TO answer_question_foreign_key');
        $this->addSql('ALTER TABLE answers RENAME INDEX idx_50d0c606a76ed395 TO answer_user_foreign_key');
        $this->addSql('ALTER TABLE courses DROP FOREIGN KEY FK_A9A55A4CA8428AFA');
        $this->addSql('ALTER TABLE courses DROP FOREIGN KEY FK_A9A55A4CD62D0EB5');
        $this->addSql('DROP INDEX IDX_A9A55A4CA8428AFA ON courses');
        $this->addSql('DROP INDEX IDX_A9A55A4CD62D0EB5 ON courses');
        $this->addSql('ALTER TABLE courses ADD id_subject INT NOT NULL, DROP $id_test, DROP $idSubject, CHANGE name name VARCHAR(50) NOT NULL, CHANGE difficulty difficulty VARCHAR(50) NOT NULL');
        $this->addSql('ALTER TABLE courses ADD CONSTRAINT course_subject_foreign_key FOREIGN KEY (id_subject) REFERENCES subjects (id) ON UPDATE CASCADE ON DELETE CASCADE');
        $this->addSql('CREATE INDEX fk_id_subject ON courses (id_subject)');
        $this->addSql('ALTER TABLE participations DROP FOREIGN KEY FK_FDC6C6E86B3CA4B');
        $this->addSql('ALTER TABLE participations DROP FOREIGN KEY FK_FDC6C6E8ED97CA4');
        $this->addSql('ALTER TABLE participations CHANGE id_user id_user INT NOT NULL, CHANGE id_session id_session INT NOT NULL');
        $this->addSql('ALTER TABLE participations ADD CONSTRAINT participation_user_foreign_key FOREIGN KEY (id_user) REFERENCES users (id) ON UPDATE CASCADE ON DELETE CASCADE');
        $this->addSql('ALTER TABLE participations ADD CONSTRAINT participation_session_foreign_key FOREIGN KEY (id_session) REFERENCES sessions (id) ON UPDATE CASCADE ON DELETE CASCADE');
        $this->addSql('ALTER TABLE participations RENAME INDEX idx_fdc6c6e8ed97ca4 TO participation_session_foreign_key');
        $this->addSql('ALTER TABLE participations RENAME INDEX idx_fdc6c6e86b3ca4b TO participation_user_foreign_key');
        $this->addSql('ALTER TABLE questions DROP FOREIGN KEY FK_8ADC54D523EDC87');
        $this->addSql('ALTER TABLE questions DROP FOREIGN KEY FK_8ADC54D5A76ED395');
        $this->addSql('ALTER TABLE questions CHANGE subject_id subject_id INT NOT NULL, CHANGE user_id user_id INT NOT NULL, CHANGE title title TEXT NOT NULL, CHANGE description description TEXT NOT NULL, CHANGE created_at created_at DATETIME DEFAULT \'current_timestamp()\' NOT NULL');
        $this->addSql('ALTER TABLE questions ADD CONSTRAINT question_subject_foreign_key FOREIGN KEY (subject_id) REFERENCES subjects (id) ON UPDATE CASCADE ON DELETE CASCADE');
        $this->addSql('ALTER TABLE questions ADD CONSTRAINT question_user_foreign_key FOREIGN KEY (user_id) REFERENCES users (id) ON UPDATE CASCADE ON DELETE CASCADE');
        $this->addSql('ALTER TABLE questions RENAME INDEX idx_8adc54d523edc87 TO fk_subject_id');
        $this->addSql('ALTER TABLE questions RENAME INDEX idx_8adc54d5a76ed395 TO fk_user_id');
        $this->addSql('ALTER TABLE resources DROP FOREIGN KEY FK_EF66EBAE613FECDF');
        $this->addSql('DROP INDEX IDX_EF66EBAE613FECDF ON resources');
        $this->addSql('ALTER TABLE resources ADD id_session INT NOT NULL, DROP session_id, CHANGE name name VARCHAR(50) NOT NULL, CHANGE file_path file_path VARCHAR(500) NOT NULL');
        $this->addSql('ALTER TABLE resources ADD CONSTRAINT resource_session_foreign_key FOREIGN KEY (id_session) REFERENCES sessions (id) ON UPDATE CASCADE ON DELETE CASCADE');
        $this->addSql('CREATE INDEX resource_session_foreign_key ON resources (id_session)');
        $this->addSql('ALTER TABLE sessions DROP FOREIGN KEY FK_9A609D1330A9DA54');
        $this->addSql('ALTER TABLE sessions DROP FOREIGN KEY FK_9A609D136B3CA4B');
        $this->addSql('ALTER TABLE sessions CHANGE id_course id_course INT NOT NULL, CHANGE id_user id_user INT NOT NULL, CHANGE date date DATE NOT NULL, CHANGE start_time start_time TIME NOT NULL, CHANGE end_time end_time TIME NOT NULL, CHANGE places places TINYINT(1) DEFAULT NULL, CHANGE meet_link meet_link VARCHAR(500) DEFAULT \'NULL\', CHANGE image_link image_link VARCHAR(500) DEFAULT \'NULL\'');
        $this->addSql('ALTER TABLE sessions ADD CONSTRAINT session_course FOREIGN KEY (id_course) REFERENCES courses (id) ON UPDATE CASCADE ON DELETE CASCADE');
        $this->addSql('ALTER TABLE sessions ADD CONSTRAINT session_user_foreign_key FOREIGN KEY (id_user) REFERENCES users (id) ON UPDATE CASCADE ON DELETE CASCADE');
        $this->addSql('ALTER TABLE sessions RENAME INDEX idx_9a609d136b3ca4b TO session_user_foreign_key');
        $this->addSql('ALTER TABLE sessions RENAME INDEX idx_9a609d1330a9da54 TO session_course');
        $this->addSql('ALTER TABLE subjects CHANGE classes_esprit classes_esprit VARCHAR(100) NOT NULL');
        $this->addSql('CREATE UNIQUE INDEX unique_sbject_name ON subjects (name)');
        $this->addSql('ALTER TABLE tests DROP FOREIGN KEY FK_1260FC5E23EDC87');
        $this->addSql('ALTER TABLE tests DROP FOREIGN KEY FK_1260FC5E591CC992');
        $this->addSql('ALTER TABLE tests DROP FOREIGN KEY FK_1260FC5E535F620E');
        $this->addSql('DROP INDEX IDX_1260FC5E23EDC87 ON tests');
        $this->addSql('DROP INDEX IDX_1260FC5E591CC992 ON tests');
        $this->addSql('DROP INDEX IDX_1260FC5E535F620E ON tests');
        $this->addSql('ALTER TABLE tests ADD id_subject INT DEFAULT NULL, ADD id_course INT DEFAULT NULL, DROP subject_id, DROP course_id, DROP id_test, CHANGE type type VARCHAR(100) NOT NULL');
        $this->addSql('ALTER TABLE tests ADD CONSTRAINT test_course_foreign_key FOREIGN KEY (id_course) REFERENCES courses (id) ON UPDATE CASCADE ON DELETE CASCADE');
        $this->addSql('ALTER TABLE tests ADD CONSTRAINT test_subject_foreign_key FOREIGN KEY (id_subject) REFERENCES subjects (id) ON UPDATE CASCADE ON DELETE CASCADE');
        $this->addSql('CREATE INDEX tests_id_subject_fk ON tests (id_subject)');
        $this->addSql('CREATE INDEX tests_id_course_fk ON tests (id_course)');
        $this->addSql('ALTER TABLE test_qs DROP FOREIGN KEY FK_1F9519C71E5D0459');
        $this->addSql('ALTER TABLE test_qs CHANGE optiona optionA VARCHAR(500) NOT NULL, CHANGE optionb optionB VARCHAR(500) NOT NULL, CHANGE optionc optionC VARCHAR(500) NOT NULL, CHANGE optiond optionD VARCHAR(500) NOT NULL, CHANGE correct_option correct_option VARCHAR(500) NOT NULL, CHANGE question question VARCHAR(500) NOT NULL');
        $this->addSql('ALTER TABLE test_qs ADD CONSTRAINT test_qs_test_foreign_key FOREIGN KEY (test_id) REFERENCES tests (id) ON UPDATE CASCADE ON DELETE CASCADE');
        $this->addSql('ALTER TABLE test_qs RENAME INDEX idx_1f9519c71e5d0459 TO test_qs_test_foreign_key');
        $this->addSql('ALTER TABLE test_results DROP FOREIGN KEY FK_43E230DC1E5D0459');
        $this->addSql('ALTER TABLE test_results DROP FOREIGN KEY FK_43E230DCA76ED395');
        $this->addSql('DROP INDEX UNIQ_43E230DC1E5D0459 ON test_results');
        $this->addSql('DROP INDEX UNIQ_43E230DCA76ED395 ON test_results');
        $this->addSql('ALTER TABLE test_results ADD id_user INT NOT NULL, ADD id_test INT NOT NULL, DROP test_id, DROP user_id');
        $this->addSql('ALTER TABLE test_results ADD CONSTRAINT test_results_test_foreign_key FOREIGN KEY (id_test) REFERENCES tests (id) ON UPDATE CASCADE ON DELETE CASCADE');
        $this->addSql('ALTER TABLE test_results ADD CONSTRAINT test_results_user_foreign_key FOREIGN KEY (id_user) REFERENCES users (id) ON UPDATE CASCADE ON DELETE CASCADE');
        $this->addSql('CREATE INDEX test_results_test_foreign_key ON test_results (id_test)');
        $this->addSql('CREATE INDEX test_results_user_foreign_key ON test_results (id_user)');
        $this->addSql('ALTER TABLE users CHANGE bio bio VARCHAR(500) DEFAULT \'NULL\', CHANGE avatar_path avatar_path VARCHAR(300) DEFAULT \'NULL\', CHANGE score score INT DEFAULT 0 NOT NULL, CHANGE type type VARCHAR(50) DEFAULT \'\'\'STUDENT\'\'\' NOT NULL, CHANGE warnings warnings TINYINT(1) DEFAULT 0 NOT NULL');
        $this->addSql('ALTER TABLE votes DROP FOREIGN KEY FK_518B7ACFAA334807');
        $this->addSql('ALTER TABLE votes DROP FOREIGN KEY FK_518B7ACF6B3CA4B');
        $this->addSql('ALTER TABLE votes CHANGE answer_id answer_id INT NOT NULL, CHANGE vote_type vote_type TINYINT(1) DEFAULT NULL');
        $this->addSql('ALTER TABLE votes ADD CONSTRAINT vote_answer_foreign_key FOREIGN KEY (answer_id) REFERENCES answers (id) ON UPDATE CASCADE ON DELETE CASCADE');
        $this->addSql('ALTER TABLE votes ADD CONSTRAINT vote_user_foreign_key FOREIGN KEY (id_user) REFERENCES users (id) ON UPDATE CASCADE ON DELETE CASCADE');
        $this->addSql('ALTER TABLE votes RENAME INDEX idx_518b7acf6b3ca4b TO vote_user_foreign_key');
        $this->addSql('ALTER TABLE votes RENAME INDEX idx_518b7acfaa334807 TO vote_answer_foreign_key');
    }
}
