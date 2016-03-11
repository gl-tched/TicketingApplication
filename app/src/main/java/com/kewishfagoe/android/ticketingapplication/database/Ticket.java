package com.kewishfagoe.android.ticketingapplication.database;

import java.util.Date;

/**
 * Created by Kemble on 3/10/2016.
 */
public class Ticket {
    private int ticket_id;
    private Date creatie_datum;
    private String type_probleem;
    private String title;
    private String description;
    private Date reparatie_datum;
    private String status;

    public int getTicket_id() {
        return ticket_id;
    }

    public void setTicket_id(int ticket_id) {
        this.ticket_id = ticket_id;
    }

    public Date getCreatie_datum() {
        return creatie_datum;
    }

    public void setCreatie_datum(Date creatie_datum) {
        this.creatie_datum = creatie_datum;
    }

    public String getType_probleem() {
        return type_probleem;
    }

    public void setType_probleem(String type_probleem) {
        this.type_probleem = type_probleem;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getReparatie_datum() {
        return reparatie_datum;
    }

    public void setReparatie_datum(Date reparatie_datum) {
        this.reparatie_datum = reparatie_datum;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
