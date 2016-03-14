package com.kewishfagoe.android.ticketingapplication.database;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Kemble on 3/14/2016.
 */
abstract class SQLiteScript {
    private static final String CREATE_TABLE_USERS = getSQLCreateTableUsers();
    private static final String CREATE_TABLE_TICKETS = getSQLCreateTableTickets();
    private static final String CREATE_TABLE_USERTICKETS = getSQLCreateTableUserTickets();
    private static final String INSERT_USERS = getSQLInsertUsers();
    private static final String INSERT_TICKETS = getSQLInsertTickets();
    private static final String INSERT_USERTICKETS = getSQLInsertUserTickets();

    public static void prepareDatabase(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_TICKETS);
        db.execSQL(CREATE_TABLE_USERTICKETS);
        db.execSQL(INSERT_USERS);
        db.execSQL(INSERT_TICKETS);
        db.execSQL(INSERT_USERTICKETS);
    }

    private static String getSQLCreateTableUsers() {
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE IF NOT EXISTS `%s` (");
        sb.append("`%s`	INTEGER NOT NULL CHECK(user_id > 0),");
        sb.append("`%s` NUMERIC NOT NULL,");
        sb.append("`%s`	TEXT NOT NULL,");
        sb.append("`%s`	TEXT NOT NULL,");
        sb.append("`%s`	TEXT NOT NULL,");
        sb.append("`%s`	NUMERIC NOT NULL UNIQUE,");
        sb.append("`%s`	TEXT UNIQUE,");
        sb.append("`%s`	TEXT NOT NULL UNIQUE,");
        sb.append("`%s`	TEXT NOT NULL,");
        sb.append("`%s`	INTEGER NOT NULL CHECK(user_level = 1 OR user_level = 2),");
        sb.append("PRIMARY KEY(user_id)");
        sb.append(");");
        return String.format(sb.toString(),
                DatabaseDAO.getTableUsersName(),
                DatabaseDAO.getTableUsersId(),
                DatabaseDAO.getTableUsersRegDate(),
                DatabaseDAO.getTableUsersFnaam(),
                DatabaseDAO.getTableUsersVnaam(),
                DatabaseDAO.getTableUsersAdres(),
                DatabaseDAO.getTableUsersTelefoon(),
                DatabaseDAO.getTableUsersEmail(),
                DatabaseDAO.getTableUsersUsername(),
                DatabaseDAO.getTableUsersPassword(),
                DatabaseDAO.getTableUsersLevel());
    }

    private static String getSQLCreateTableTickets() {
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE IF NOT EXISTS `%s` (");
        sb.append("`%s`	INTEGER NOT NULL CHECK(ticket_id > 0),");
        sb.append("`%s`	NUMERIC NOT NULL,");
        sb.append("`%s`	TEXT CHECK(type_probleem = 'SOFTWARE' OR type_probleem = 'HARDWARE'),");
        sb.append("`%s`	TEXT NOT NULL UNIQUE,");
        sb.append("`%s`	TEXT NOT NULL,");
        sb.append("`%s`	NUMERIC,");
        sb.append("`%s`	TEXT NOT NULL CHECK(status = 'OPEN' OR status = 'IN PROGRESS' OR status = 'CLOSED'),");
        sb.append("PRIMARY KEY(ticket_id)");
        sb.append(");");
        return String.format(sb.toString(),
                DatabaseDAO.getTableTicketsName(),
                DatabaseDAO.getTableTicketsId(),
                DatabaseDAO.getTableTicketsCreationDate(),
                DatabaseDAO.getTableTicketsTypeProbleem(),
                DatabaseDAO.getTableTicketsTitle(),
                DatabaseDAO.getTableTicketsDescription(),
                DatabaseDAO.getTableTicketsReparatieDatum(),
                DatabaseDAO.getTableTicketsStatus());
    }

    private static String getSQLCreateTableUserTickets() {
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE IF NOT EXISTS `%s` (");
        sb.append("`%s`	INTEGER NOT NULL CHECK(user_tickets_id > 0),");
        sb.append("`%s`	INTEGER NOT NULL,");
        sb.append("`%s`	INTEGER NOT NULL,");
        sb.append("PRIMARY KEY(user_tickets_id),");
        sb.append("FOREIGN KEY(user_id) REFERENCES users(user_id),");
        sb.append("FOREIGN KEY(ticket_id) REFERENCES tickets(ticket_id)");
        sb.append(");");
        return String.format(sb.toString(),
                DatabaseDAO.getTableUserTicketsName(),
                DatabaseDAO.getTableUserTicketsId(),
                DatabaseDAO.getTableUserTicketsUid(),
                DatabaseDAO.getTableUserTicketsTid());
    }

    private static String getSQLInsertUsers() {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO `%s` (`%s`,`%s`,`%s`,`%s`,`%s`,`%s`,`%s`,`%s`,`%s`) VALUES ");
        sb.append("(datetime('2015-01-18'),'Bollinger','Joyce','Windmolen 181',8693447,'JoyceJBollinger@inbound.plus','joyce','5dafa4f662d29c5f870ba55a880dc5089226721f183543cdfaec0c21ccd8d63c',2),");
        sb.append("(datetime('2015-01-22'),'Phillips','Laura','3287 Heritage Road',8943624,'LauraJPhillips@inbound.plus','laura','33279da8b5b6c335508199cd0c4be818a000560748ffd4426c16cf337edc4388',2),");
        sb.append("(datetime('2016-01-19'),'Marskamp','Hossein','24 Union Terrace',8245849,'HosseinMarskamp@inbound.plus','hossein','2bb7ea9c58cab9c5830ef01aca1ed22347fee89bd68885a9881be76ec0dc5e43',2),");
        sb.append("(datetime('2015-01-23'),'Garrison','Sharon','72 Ermin Street',8634327,'SharonTGarrison@inbound.plus','sharon','0e790d27774f7882a2751c606ab5a8060a2420cb5fbc5ad9de57e812635d3c47',2),");
        sb.append("(datetime('2015-01-25'),'Tanner','Rachel','65 Old Edinburgh Road',8842392,'RachelLTanner@inbound.plus','rachel','7e7927bce8281ec376710c7ce27495641a5f2e9d49da626dc69a6dc1fe7e055d',2),");
        sb.append("(datetime('2015-01-27'),'Weller','Nicholas','7 Thames Street',8942328,'NicholasWeller@inbound.plus','nicholas','12b3f058901cbd7a4ec5591ed742d392e06e6238cd5d846720b6e3ccea71d822',2),");
        sb.append("(datetime('2015-01-28'),'Flink','Fidan','8 Fox Lane',8233474,'FidanFlink@inbound.plus','fidan','feffc45ae2d1cc85e3aeddd4fb9e2359993435a92cd3e6853806e5e9d796b8e5',2),");
        sb.append("(datetime('2015-01-29'),'Hightower','Jenna','Wethouder Kentiestraat 52',8653923,'JennaDHightower@inbound.plus','jenna','88904f12a19c27ef172af5556eadc603462e35dc279ae10d83d10af0ef668c5f',2),");
        sb.append("(datetime('2015-01-27'),'Comes','John','4158 Davis Court',8942346,'JohnKComes@inbound.plus','john','712a1aec87e80762d3ffc9701acde1b997a3049e5ac5d5c82c856d520221186a',2),");
        sb.append("(datetime('2016-02-03'),'King','Katie','787 Wakefield Street',8842429,'KatieFKing@inbound.plus','katie','ae1ab49a5348a6ba6e03c7eabdf2a486fb973973645d60a6e8c7a5cb4ce9e6c0',2),");
        sb.append("(datetime('2015-01-05'),'Super','Admin','Column St. 123',8932618,'AdminSuper@inbound.plus','sadmin','9f5ba68f21489544d985797d58847b65e9a22c4981aeccafc96b351e84df254c',1);");
        return String.format(sb.toString(),
                DatabaseDAO.getTableUsersName(),
                DatabaseDAO.getTableUsersRegDate(),
                DatabaseDAO.getTableUsersFnaam(),
                DatabaseDAO.getTableUsersVnaam(),
                DatabaseDAO.getTableUsersAdres(),
                DatabaseDAO.getTableUsersTelefoon(),
                DatabaseDAO.getTableUsersEmail(),
                DatabaseDAO.getTableUsersUsername(),
                DatabaseDAO.getTableUsersPassword(),
                DatabaseDAO.getTableUsersLevel());
    }

    private static String getSQLInsertTickets() {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO `%s` (`%s`,`%s`,`%s`,`%s`,`%s`,`%s`) VALUES ");
        sb.append("(datetime('2015-01-18'),'SOFTWARE','MS Word Probleem','MS Word gaat niet open. Geeft x50326 error bij opstart.',datetime('2015-01-22'),'CLOSED'),");
        sb.append("(datetime('2015-01-22'),'SOFTWARE','Photoshop Probleem','Photoshop Crashed. Geeft error bij specifiek tool.',datetime('2015-01-23'),'CLOSED'),");
        sb.append("(datetime('2015-01-22'),'HARDWARE','RAM Probleem','Zwarte scherm bij opstart.',datetime('2015-01-24'),'CLOSED'),");
        sb.append("(datetime('2015-01-22'),'SOFTWARE','Wireless Probleem','Wifi connect naar geen enkel network.',datetime('2015-01-25'),'CLOSED'),");
        sb.append("(datetime('2016-01-19'),'HARDWARE','Scherm Probleem','Monitor heeft een groen hue erover.',datetime('2016-01-25'),'CLOSED'),");
        sb.append("(datetime('2015-01-24'),'SOFTWARE','Chrome Probleem','Start heel erg traag op.',datetime('2015-01-26'),'CLOSED'),");
        sb.append("(datetime('2015-01-24'),'HARDWARE','Keyboard Probleem','De knoppen wasd werken niet meer.',datetime('2015-01-26'),'CLOSED'),");
        sb.append("(datetime('2015-01-23'),'SOFTWARE','Windows Explorer Probleem','Windows explorer crasht bij selecteren van meerdere folders.',datetime('2015-01-27'),'IN PROGRESS'),");
        sb.append("(datetime('2015-01-25'),'SOFTWARE','VPN Connectie probleem','Aanmaken van nieuwe vpn connectie in win7 lukt niet.',datetime('2015-01-27'),'IN PROGRESS'),");
        sb.append("(datetime('2016-01-26'),'HARDWARE','Disk Drive probleem','Disk drive maakt een vreemde geluid en leest schijven niet meer.',datetime('2016-01-28'),'IN PROGRESS'),");
        sb.append("(datetime('2015-01-26'),'SOFTWARE','Laptop webcam Probleem','Geeft soms wel en soms geen beeld.',datetime('2015-01-29'),'IN PROGRESS'),");
        sb.append("(datetime('2015-01-27'),'HARDWARE','Externe Webcam Probleem','Geeft geen beeld meer.',datetime('2015-01-30'),'IN PROGRESS'),");
        sb.append("(datetime('2016-01-29'),'HARDWARE','Overheating probleem','CPU Fan is slow geworden.',datetime('2016-01-30'),'IN PROGRESS'),");
        sb.append("(datetime('2015-01-28'),'SOFTWARE','Audio Probleem','Er komt geen geluid uit de laptop.',datetime('2015-01-31'),'IN PROGRESS'),");
        sb.append("(datetime('2015-01-29'),'SOFTWARE','Power Probleem','Computer crashed BSOD.',datetime('2015-02-01'),'OPEN'),");
        sb.append("(datetime('2015-02-01'),'HARDWARE','Monitor Probleem','Monitor gaat niet aan.',datetime('2015-02-02'),'OPEN'),");
        sb.append("(datetime('2016-02-01'),'HARDWARE','HDD Probleem','Machine start niet op. Zegt het kan geen HDD vinden.',datetime('2016-02-03'),'OPEN'),");
        sb.append("(datetime('2015-01-27'),'SOFTWARE','Media Probleem','Media Files gaan niet open in Windows Media Player.',datetime('2015-01-30'),'OPEN'),");
        sb.append("(datetime('2015-02-01'),'SOFTWARE','Documenten opslag Probleem','MS Word kan geen documenten meer opslaan.',datetime('2015-02-04'),'OPEN'),");
        sb.append("(datetime('2016-02-03'),'SOFTWARE','Excel Probleem','Excel files gaat niet meer open. Crasht bij het openmaken van een excel file.',datetime('2016-02-05'),'OPEN');");
        return String.format(sb.toString(),
                DatabaseDAO.getTableTicketsName(),
                DatabaseDAO.getTableTicketsCreationDate(),
                DatabaseDAO.getTableTicketsTypeProbleem(),
                DatabaseDAO.getTableTicketsTitle(),
                DatabaseDAO.getTableTicketsDescription(),
                DatabaseDAO.getTableTicketsReparatieDatum(),
                DatabaseDAO.getTableTicketsStatus());
    }

    private static String getSQLInsertUserTickets() {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO `%s` (`%s`,`%s`) VALUES ");
        sb.append("(1,1), (1,2), (1,3), (2,4), (3,5), (3,6), (4,7), (4,8), (5,9), (5,10), ");
        sb.append("(5,11), (6,12), (7,13), (7,14), (8,15), (8,16), (9,17),  (9,18), (9,19), (10,20);");
        return String.format(sb.toString(),
                DatabaseDAO.getTableUserTicketsName(),
                DatabaseDAO.getTableUserTicketsUid(),
                DatabaseDAO.getTableUserTicketsTid());
    }
}
