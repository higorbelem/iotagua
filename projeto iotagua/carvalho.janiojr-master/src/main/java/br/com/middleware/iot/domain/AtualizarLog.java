/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.middleware.iot.domain;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Janio
 */
@Entity
public class AtualizarLog extends GenericDomain{
    
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date data;
     
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date horario;
    
    @Column(nullable = false)
    private String descricao;
    
    @Column(nullable = false)
    private String historico;

    public String getHistorico() {
        return historico;
    }

    public void setHistorico(String historico) {
        this.historico = historico;
    }
    

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Date getHorario() {
        return horario;
    }

    public void setHorario(Date horario) {
        this.horario = horario;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
    
}
