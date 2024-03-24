package br.com.hyperativa.cardmanagement.model;

import org.springframework.web.multipart.MultipartFile;

public class Arquivo {
    private String nome;
    private MultipartFile arquivo;
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public MultipartFile getArquivo() {
		return arquivo;
	}
	public void setArquivo(MultipartFile arquivo) {
		this.arquivo = arquivo;
	}

    // getters e setters
}