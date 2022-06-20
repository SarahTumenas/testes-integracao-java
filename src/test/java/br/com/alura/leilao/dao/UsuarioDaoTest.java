package br.com.alura.leilao.dao;

import br.com.alura.leilao.model.Usuario;
import br.com.alura.leilao.util.JpaUtil;
import org.junit.Assert;
import org.junit.jupiter.api.Test;


import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

class UsuarioDaoTest {

    private UsuarioDao dao;


    @Test
    void deveriaEncontrarUsuarioCadastrado() {

        EntityManager em = JpaUtil.getEntityManager();

        this.dao = new UsuarioDao(em);

        Usuario usuario = new Usuario("fulano", "fulano@email.com", "12345678");
        em.getTransaction().begin();
        em.persist(usuario);
        em.getTransaction().commit();

        Usuario encontrado = this.dao.buscarPorUsername(usuario.getNome());
        Assert.assertNotNull(encontrado);

    }

    @Test
    void naoDeveriaEncontrarUsuarioNaoCadastrado() {

        EntityManager em = JpaUtil.getEntityManager();

        this.dao = new UsuarioDao(em);

        Usuario usuario = new Usuario("fulano", "fulano@email.com", "12345678");
        em.getTransaction().begin();
        em.persist(usuario);
        em.getTransaction().commit();

        Assert.assertThrows(NoResultException.class, () -> this.dao.buscarPorUsername("beltrano"));

    }
}
