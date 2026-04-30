package com.platanitos.springplatanitos.services.producto;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.platanitos.springplatanitos.models.ProductoVariante;
import com.platanitos.springplatanitos.repository.producto.ProductoVarianteRepository;

@Service
public class ProductoVarianteServices {

    @Autowired
    private ProductoVarianteRepository productoVarianteRepository;

    //Metodo para crear una nueva variante de producto
    public ProductoVariante crearVariante(ProductoVariante nuevaVariante){
        return productoVarianteRepository.save(nuevaVariante);
    }
    
    //Metodo para obtener las variantes de un producto por su id
    public List<ProductoVariante> obtenerVariantesPorProductoId(Long idProducto){
        return productoVarianteRepository.findByProductoIdAndEstadoTrue(idProducto);
    }

    //Metodo para obtener una variante por su id
    public ProductoVariante obtenerVariantePorId(Long id){
        return productoVarianteRepository.findById(id)
                                        .orElse(null);
    }

    //Metodo para actualizar una variante de producto
    public ProductoVariante actualizarVariante(Long id, ProductoVariante varianteActualizada){
        Optional<ProductoVariante> productoActualizar = productoVarianteRepository.findById(id);
        if(!productoActualizar.isPresent())
            return null;            

        ProductoVariante variante = productoActualizar.get();
        variante.setProducto(varianteActualizada.getProducto());
        variante.setColor(varianteActualizada.getColor());
        variante.setTalla(varianteActualizada.getTalla());
        variante.setStock(varianteActualizada.getStock());
        variante.setPrecio(varianteActualizada.getPrecio());
        return productoVarianteRepository.save(variante);
    }

    //Metodo para cambiar el estado de una variante (softDelete)
    public ProductoVariante cambiarEstado(Long id){
        Optional<ProductoVariante> varianteExistente = productoVarianteRepository.findById(id);

        if(varianteExistente.isPresent()){
            ProductoVariante variante = varianteExistente.get();
            variante.setEstado(!variante.getEstado());
            return productoVarianteRepository.save(variante);
        } else 
            return null;   
    }

    //Metodo para obtener todas las variantes existentes
    public List<ProductoVariante> todasLasVariantes(){
        return productoVarianteRepository.findAllByEstadoTrue();
    }

    //Metodo para contar el numero de variantes que existen con una talla y color especificos
    public Integer contarVariantesPorTallaYColor(String talla, String color, Long idProducto){
        return productoVarianteRepository.countByTalla_TallaAndColor_ColorAndProductoId(talla, color, idProducto);
    }

}
