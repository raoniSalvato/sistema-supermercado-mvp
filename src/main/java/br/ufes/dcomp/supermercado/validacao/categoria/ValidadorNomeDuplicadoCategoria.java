package br.ufes.dcomp.supermercado.validacao.categoria;

import br.ufes.dcomp.supermercado.model.Categoria;
import br.ufes.dcomp.supermercado.repositorio.CategoriaRepository;
import java.util.List;

public class ValidadorNomeDuplicadoCategoria extends ValidadorCategoriaHandler {
    
    private CategoriaRepository repository;

    public ValidadorNomeDuplicadoCategoria(CategoriaRepository repository) {
        this.repository = repository;
    }

    @Override
    protected void realizarValidacao(Categoria categoria) throws RuntimeException {
        List<Categoria> categoriasExistentes = repository.buscarTodas();
        for (Categoria categoriaExistente : categoriasExistentes) {
            if (!categoriaExistente.getId().equals(categoria.getId())) {
                if (categoriaExistente.getNome().equalsIgnoreCase(categoria.getNome())) {
                    throw new RuntimeException("Já existe uma categoria cadastrada com este nome.");
                }
            }
        }
    }
}