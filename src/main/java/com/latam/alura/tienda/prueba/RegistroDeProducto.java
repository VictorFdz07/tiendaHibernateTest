package com.latam.alura.tienda.prueba;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

import com.latam.alura.tienda.dao.CategoriaDao;
import com.latam.alura.tienda.dao.ProductoDao;
import com.latam.alura.tienda.modelo.Categoria;
import com.latam.alura.tienda.modelo.Producto;
import com.latam.alura.tienda.utils.JPAUtils;

public class RegistroDeProducto {

	public static void main(String[] args) {
		registrarProducto();
		EntityManager em = JPAUtils.getEntityManager();
		
		ProductoDao productoDao = new ProductoDao(em);
		
		Producto producto = productoDao.consultaPorId(1l);
		
		System.out.println(producto.getNombre());
		
		List<Producto> productos = productoDao.consultaPorNombreDeCategoria("CELULARES");
		
		for (Producto p : productos) {
			System.out.println(p.getDescripcion());
		}
		
		BigDecimal precioProducto = productoDao.consultarPrecioPorNombreDeProducto("Samsung");
		System.out.println(precioProducto);
	}

	private static void registrarProducto() {
		Categoria celulares = new Categoria("CELULARES");
		Producto celular = new Producto("Samsung", "telefoon nuevo", new BigDecimal("1000"), celulares);
		
		EntityManager em = JPAUtils.getEntityManager();
		
		ProductoDao productoDao = new ProductoDao(em);
		CategoriaDao categoriaDao = new CategoriaDao(em);
		
		em.getTransaction().begin();
		
		categoriaDao.guardar(celulares);
		productoDao.guardar(celular);
		
		em.getTransaction().commit();
		em.close();
		
		//V1
		/*
		em.persist(celulares);
		celulares.setNombre("LAPTOPS");
		em.getTransaction().commit();
		em.close();
		celulares.setNombre("PANTALLAS");
		*/

		//V2
		/*
		em.persist(celulares);
		celulares.setNombre("LAPTOPS");
		em.flush();
		em.clear();

		celulares = em.merge(celulares);
		celulares.setNombre("PANTALLAS");
		em.flush();
		em.remove(celulares);
		*/

		//V3
		/*
		em.persist(celulares);
		celulares.setNombre("LAPTOPS");
		em.flush();
		em.clear();

		celulares = em.merge(celulares);
		celulares.setNombre("PANTALLAS");
		em.flush();
		//em.clear();
		em.remove(celulares);
		em.flush();
		*/
	}

}
