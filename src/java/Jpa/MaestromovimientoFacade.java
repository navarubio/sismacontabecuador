/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Jpa;

import Modelo.Empresa;
import Modelo.Maestromovimiento;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author sofimar
 */
@Stateless
public class MaestromovimientoFacade extends AbstractFacade<Maestromovimiento> implements MaestromovimientoFacadeLocal{
    @PersistenceContext(unitName = "SismacontabecPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MaestromovimientoFacade() {
        super(Maestromovimiento.class);
    }
    
    @Override
    public List<Maestromovimiento> MovimientosOrdenadosFecha(Empresa empre) {
        String consulta;
        List<Maestromovimiento> lista = null;
        try {
            consulta = "From Maestromovimiento m where m.idempresa = ?1 order by m.fechamovimiento, m.idmaestro";
            Query query = em.createQuery(consulta);
            query.setParameter(1, empre.getIdempresa());
            lista = query.getResultList();
        } catch (Exception e) {
            throw e;
        }
        return lista;
    }
    
}
