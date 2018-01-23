package com.our_software_factory.medicinedonate;

public class Medicine {
    private String apresentacao;
    private String laboratorio;
    private String nome;
    private String principio_ativo;
    private String tarja;
    private String tipo_de_produto;

    public Medicine(){}

    public Medicine(String apresentacao, String laboratorio, String nome, String principio_ativo, String tarja, String tipo_de_produto) {
        this.apresentacao = apresentacao;
        this.laboratorio = laboratorio;
        this.nome = nome;
        this.principio_ativo = principio_ativo;
        this.tarja = tarja;
        this.tipo_de_produto = tipo_de_produto;
    }

    public String getApresentacao() {
        return apresentacao;
    }

    public String getLaboratorio() {
        return laboratorio;
    }

    public String getNome() {
        return nome;
    }

    public String getPrincipio_ativo() {
        return principio_ativo;
    }

    public String getTarja() {
        return tarja;
    }

    public String getTipo_de_produto() {
        return tipo_de_produto;
    }
}
