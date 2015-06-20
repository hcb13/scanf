/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufg.jatai.ps.beans;

import br.ufg.jatai.ps.dao.AlunoDAO;
import br.ufg.jatai.ps.dao.jpa.FabricaDAOJPA;
import br.ufg.jatai.ps.modelo.Aluno;
import br.ufg.jatai.ps.util.Mensagens;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "baluno")
@SessionScoped
public class AlunoBean {

    private final AlunoDAO aDAO = (new FabricaDAOJPA()).obterAlunoDAO();
    private Aluno aluno = new Aluno();
    private Aluno alunoSessao = null;
    private String confirmarSenha;

    public boolean estahLogado() {
        return (alunoSessao != null);
    }
    
    public String cadastrarAluno() {
        if (!confirmarSenha.equals(aluno.getSenha())) {
            Mensagens.adicionarMensagem(
                    FacesMessage.SEVERITY_ERROR,
                    "As senhas informadas não conferem!");
            return null;
        }
        if (aDAO.existeAlunoComEmail(aluno.getEmail())) {
            Mensagens.adicionarMensagem(
                    FacesMessage.SEVERITY_ERROR,
                    "Usuário já cadastrado com esse email!");
            return null;
        }
        aDAO.salvar(aluno);
        aluno = new Aluno();
        return "login.xhtml?faces-redirect=true";
    }

    public String autenticar() {
        alunoSessao = aDAO.obterAlunoPorEmailESenha(aluno.getEmail(), aluno.getSenha());
        if (alunoSessao == null)  {
            Mensagens.adicionarMensagem(
                    FacesMessage.SEVERITY_ERROR,
                    "Email ou senha inválidos!");
            return null;
        }
        return "minhasDisciplinas.xhtml?faces-redirect=true";
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public String getConfirmarSenha() {
        return confirmarSenha;
    }

    public void setConfirmarSenha(String confirmarSenha) {
        this.confirmarSenha = confirmarSenha;
    }

    public Aluno getAlunoSessao() {
        return alunoSessao;
    }

    public void setAlunoSessao(Aluno alunoSessao) {
        this.alunoSessao = alunoSessao;
    }
    
}