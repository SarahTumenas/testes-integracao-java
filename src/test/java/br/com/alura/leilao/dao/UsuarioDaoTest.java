package br.com.alura.leilao.dao;

import br.com.alura.leilao.model.Usuario;
import br.com.alura.leilao.util.JpaUtil;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

class UsuarioDaoTest {

    private UsuarioDao dao;

    private EntityManager em;

    @BeforeEach
    public void beforeEach() {
        this.em = new JpaUtil().getEntityManager();
        this.dao = new UsuarioDao(this.em);
        em.getTransaction().begin();
    }

    @AfterEach
    public void afterEach() {
        em.getTransaction().rollback();
    }

    @Test
    void deveriaEncontrarUsuarioCadastrado() {
        Usuario usuario = criarUsuario();
        Usuario encontrado = this.dao.buscarPorUsername(usuario.getNome());
        Assert.assertNotNull(encontrado);

    }

    @Test
    void naoDeveriaEncontrarUsuarioNaoCadastrado() {
        criarUsuario();
       Assert.assertThrows(NoResultException.class, () -> this.dao.buscarPorUsername("beltrano"));

    }

    @Test
     void deveriaRemoverUsuario() {
        Usuario usuario = criarUsuario();
        dao.deletar(usuario);
        Assert.assertThrows(NoResultException.class, () -> this.dao.buscarPorUsername(usuario.getNome()));

    }

    private Usuario criarUsuario () {
        Usuario usuario = new Usuario("fulano", "fulano@email.com", "12345678");
        em.persist(usuario);
        return usuario;
    }
}
