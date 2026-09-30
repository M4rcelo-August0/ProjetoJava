-- Sistema de Gerenciamento de Biblioteca
-- Script de criação do banco de dados (somente estrutura, sem dados)

CREATE DATABASE IF NOT EXISTS biblioteca_db
  DEFAULT CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

USE biblioteca_db;

-- MySQL dump 10.13  Distrib 8.0.46, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: biblioteca_db
-- ------------------------------------------------------
-- Server version	8.0.46

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `carteira_biblioteca`
--

DROP TABLE IF EXISTS `carteira_biblioteca`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `carteira_biblioteca` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `numero` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `data_criacao` date NOT NULL DEFAULT (curdate()),
  `status` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'ATIVA',
  `limite_emprestimos` int NOT NULL DEFAULT '3',
  `quantidade_emprestimos` int NOT NULL DEFAULT '0',
  `data_ultima_atualizacao` date DEFAULT NULL,
  `observacao` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `usuario_id` bigint unsigned NOT NULL,
  `codigo_carteira` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_carteira_numero` (`numero`),
  UNIQUE KEY `uk_carteira_codigo` (`codigo_carteira`),
  UNIQUE KEY `uk_carteira_usuario` (`usuario_id`),
  CONSTRAINT `fk_carteira_usuario` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `ck_carteira_limite` CHECK ((`limite_emprestimos` >= 0)),
  CONSTRAINT `ck_carteira_quantidade` CHECK (((`quantidade_emprestimos` >= 0) and (`quantidade_emprestimos` <= `limite_emprestimos`))),
  CONSTRAINT `ck_carteira_status` CHECK ((`status` in (_utf8mb4'ATIVA',_utf8mb4'INATIVA',_utf8mb4'BLOQUEADA')))
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `categoria`
--

DROP TABLE IF EXISTS `categoria`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categoria` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `nome` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `descricao` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `codigo` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `data_cadastro` date NOT NULL DEFAULT (curdate()),
  `status` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'ATIVA',
  `faixa_etaria` varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `genero` varchar(80) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `observacao` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_categoria_nome` (`nome`),
  UNIQUE KEY `uk_categoria_codigo` (`codigo`),
  CONSTRAINT `ck_categoria_status` CHECK ((`status` in (_utf8mb4'ATIVA',_utf8mb4'INATIVA')))
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `emprestimo`
--

DROP TABLE IF EXISTS `emprestimo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `emprestimo` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `data_emprestimo` date NOT NULL DEFAULT (curdate()),
  `data_prevista_devolucao` date NOT NULL,
  `data_devolucao` date DEFAULT NULL,
  `status` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'ATIVO',
  `observacao` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `usuario_id` bigint unsigned NOT NULL,
  `livro_id` bigint unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_emprestimo_usuario_status` (`usuario_id`,`status`),
  KEY `idx_emprestimo_livro_status` (`livro_id`,`status`),
  KEY `idx_emprestimo_data_prevista` (`data_prevista_devolucao`),
  CONSTRAINT `fk_emprestimo_livro` FOREIGN KEY (`livro_id`) REFERENCES `livro` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `fk_emprestimo_usuario` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `ck_emprestimo_devolucao` CHECK (((`data_devolucao` is null) or (`data_devolucao` >= `data_emprestimo`))),
  CONSTRAINT `ck_emprestimo_previsao` CHECK ((`data_prevista_devolucao` >= `data_emprestimo`)),
  CONSTRAINT `ck_emprestimo_status` CHECK ((`status` in (_utf8mb4'ATIVO',_utf8mb4'ATRASADO',_utf8mb4'DEVOLVIDO')))
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `livro`
--

DROP TABLE IF EXISTS `livro`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `livro` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `titulo` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL,
  `isbn` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  `autor` varchar(150) COLLATE utf8mb4_unicode_ci NOT NULL,
  `editora` varchar(150) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `ano_publicacao` int DEFAULT NULL,
  `quantidade_total` int NOT NULL DEFAULT '1',
  `quantidade_disponivel` int NOT NULL DEFAULT '1',
  `descricao` varchar(1000) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `categoria_id` bigint unsigned NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_livro_isbn` (`isbn`),
  KEY `fk_livro_categoria` (`categoria_id`),
  KEY `idx_livro_titulo` (`titulo`),
  CONSTRAINT `fk_livro_categoria` FOREIGN KEY (`categoria_id`) REFERENCES `categoria` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `ck_livro_ano_publicacao` CHECK (((`ano_publicacao` is null) or (`ano_publicacao` >= 0))),
  CONSTRAINT `ck_livro_quantidade_disponivel` CHECK (((`quantidade_disponivel` >= 0) and (`quantidade_disponivel` <= `quantidade_total`))),
  CONSTRAINT `ck_livro_quantidade_total` CHECK ((`quantidade_total` >= 0))
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `nome` varchar(150) COLLATE utf8mb4_unicode_ci NOT NULL,
  `email` varchar(150) COLLATE utf8mb4_unicode_ci NOT NULL,
  `cpf` varchar(14) COLLATE utf8mb4_unicode_ci NOT NULL,
  `telefone` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `endereco` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `data_nascimento` date DEFAULT NULL,
  `data_cadastro` date NOT NULL DEFAULT (curdate()),
  `status` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'ATIVO',
  `senha` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_usuario_email` (`email`),
  UNIQUE KEY `uk_usuario_cpf` (`cpf`),
  CONSTRAINT `ck_usuario_status` CHECK ((`status` in (_utf8mb4'ATIVO',_utf8mb4'INATIVO',_utf8mb4'BLOQUEADO')))
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Temporary view structure for view `vw_emprestimos_atrasados`
--

DROP TABLE IF EXISTS `vw_emprestimos_atrasados`;
/*!50001 DROP VIEW IF EXISTS `vw_emprestimos_atrasados`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `vw_emprestimos_atrasados` AS SELECT 
 1 AS `emprestimo_id`,
 1 AS `usuario_id`,
 1 AS `usuario_nome`,
 1 AS `livro_id`,
 1 AS `livro_titulo`,
 1 AS `data_emprestimo`,
 1 AS `data_prevista_devolucao`,
 1 AS `dias_atraso`*/;
SET character_set_client = @saved_cs_client;

--
-- Dumping routines for database 'biblioteca_db'
--
/*!50003 DROP PROCEDURE IF EXISTS `atualizar_bloqueios_por_atraso` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `atualizar_bloqueios_por_atraso`()
BEGIN
    -- Marca como atrasados os empréstimos vencidos e não devolvidos.
    UPDATE emprestimo
       SET status = 'ATRASADO'
     WHERE data_devolucao IS NULL
       AND data_prevista_devolucao < CURRENT_DATE
       AND status <> 'ATRASADO';

    -- Bloqueia usuários que possuem pelo menos um empréstimo atrasado.
    UPDATE usuario u
       SET u.status = 'BLOQUEADO'
     WHERE u.status <> 'INATIVO'
       AND EXISTS (
            SELECT 1
              FROM emprestimo e
             WHERE e.usuario_id = u.id
               AND e.data_devolucao IS NULL
               AND e.data_prevista_devolucao < CURRENT_DATE
       );

    -- Desbloqueia quem não possui mais empréstimos atrasados.
    -- Usuários INATIVOS não são modificados.
    UPDATE usuario u
       SET u.status = 'ATIVO'
     WHERE u.status = 'BLOQUEADO'
       AND NOT EXISTS (
            SELECT 1
              FROM emprestimo e
             WHERE e.usuario_id = u.id
               AND e.data_devolucao IS NULL
               AND e.data_prevista_devolucao < CURRENT_DATE
       );
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Final view structure for view `vw_emprestimos_atrasados`
--

/*!50001 DROP VIEW IF EXISTS `vw_emprestimos_atrasados`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `vw_emprestimos_atrasados` AS select `e`.`id` AS `emprestimo_id`,`e`.`usuario_id` AS `usuario_id`,`u`.`nome` AS `usuario_nome`,`e`.`livro_id` AS `livro_id`,`l`.`titulo` AS `livro_titulo`,`e`.`data_emprestimo` AS `data_emprestimo`,`e`.`data_prevista_devolucao` AS `data_prevista_devolucao`,(to_days(curdate()) - to_days(`e`.`data_prevista_devolucao`)) AS `dias_atraso` from ((`emprestimo` `e` join `usuario` `u` on((`u`.`id` = `e`.`usuario_id`))) join `livro` `l` on((`l`.`id` = `e`.`livro_id`))) where ((`e`.`data_devolucao` is null) and (`e`.`data_prevista_devolucao` < curdate())) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-09-29 23:02:52
