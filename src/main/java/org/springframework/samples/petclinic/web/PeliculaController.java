package org.springframework.samples.petclinic.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Merchandasing;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pelicula;
import org.springframework.samples.petclinic.model.Vendedor;
import org.springframework.samples.petclinic.model.Videojuego;
import org.springframework.samples.petclinic.service.PedidoService;
import org.springframework.samples.petclinic.service.PeliculaService;
import org.springframework.samples.petclinic.service.ProductoService;
import org.springframework.samples.petclinic.service.VendedorService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.bytebuddy.implementation.bind.MethodDelegationBinder.BindingResolver;

/**
 * @author Marta Díaz
 */
@Controller
public class PeliculaController {

	@Autowired
	private final PeliculaService peliculaService;

	@Autowired
	private final ProductoService productoService;

	@Autowired
	private final PedidoService pedidoService;

	@Autowired
	private final VendedorService vendedorService;

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@Autowired
	public PeliculaController(final PeliculaService peliculaService, final ProductoService productoService,
			final PedidoService pedidoService, final VendedorService vendedorService) {
		this.peliculaService = peliculaService;
		this.productoService = productoService;
		this.pedidoService = pedidoService;
		this.vendedorService = vendedorService;

	}

	
	@GetMapping(value = "/peliculas")
	public String showPeliculasList(Map<String, Object> model) {
		List<Pelicula> peliculas = this.peliculaService.findPeliculas();
		List<Integer> idProhibidos = this.pedidoService.listaIdPeliculasCompradas();
		List<Pelicula> peliculasPermitidas = new ArrayList<>();
		for (Pelicula pel : peliculas) {
			if (!idProhibidos.contains(pel.getId())) {
				peliculasPermitidas.add(pel);
			}
		}
		model.put("peliculas", peliculasPermitidas);
		return "/peliculas/PeliculasList";
	}

	@GetMapping(value = "/peliculas/edit/{peliculaId}")
	public String initUpdateForm(@PathVariable("peliculaId") int peliculaId, ModelMap model) {
		Pelicula p = this.peliculaService.findPeliculaById(peliculaId);
		model.put("pelicula", p);
		String view = "/peliculas/formCreatePeliculas";
		return view;
	}

	@PostMapping(value = "/peliculas/edit/{peliculaId}")
	public String processUpdatePeliculaForm(@Valid final Pelicula p, BindingResult result,
			@PathVariable("peliculaId") int peliculaId,ModelMap model) {

		String view = "/peliculas/formCreatePeliculas";

		if (result.hasErrors()) {
			model.addAttribute("message", "ERROR!");
		} else {
			p.setId(peliculaId);
			this.peliculaService.savePelicula(p);
			view = "redirect:/peliculas/" + p.getId();
			
		}
		return view;
	}

	
	@GetMapping("/peliculas/{peliculaId}")
	public String showPelicula(@PathVariable("peliculaId") int peliculaId, Map<String, Object> model) {
		Pelicula pelicula = this.peliculaService.findPeliculaById(peliculaId);
		model.put("pelicula", pelicula);
		return "/peliculas/peliculaDetails";
	}


	@GetMapping(value = "/peliculas/new")
	public String createPelicula(final ModelMap modelmap) {
		String view = "/peliculas/formCreatePeliculas";
		modelmap.addAttribute("pelicula", new Pelicula()); 
		return view;
	}

	
	@PostMapping(value = "/peliculas/new")
	public String savePelicula(@Valid Pelicula pel, BindingResult result, ModelMap model) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetail = (UserDetails) auth.getPrincipal();
		String username = userDetail.getUsername();
		Vendedor vendedor = this.vendedorService.findVendedorByUsername(username);
		
		if (result.hasErrors()) {
			return "/peliculas/formCreatePeliculas";
		} else {
			this.peliculaService.savePelicula(pel);
			if (vendedor.getPeliculas() == null) {
				Collection<Pelicula> peliculas = new ArrayList<Pelicula>();
				peliculas.add(pel);
				vendedor.getPeliculas().addAll(peliculas);
			} else {
				vendedor.getPeliculas().add(pel);
			}
			this.vendedorService.save(vendedor);
			return mostrarProductos(model);
		}
	}

	
	@GetMapping("/peliculas/delete/{peliculaId}")
	public String deletePelicula(@PathVariable("peliculaId") int peliculaId, final ModelMap modelMap) {

		Pelicula peliculaBorrar = this.peliculaService.findPeliculaById(peliculaId);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetail = (UserDetails) auth.getPrincipal();
		String username = userDetail.getUsername();
		Vendedor vendedor = this.vendedorService.findVendedorByUsername(username);
		Collection<Pelicula> peliculas = vendedor.getPeliculas();

		if ((peliculaBorrar != null) && (peliculas.contains(peliculaBorrar))) {
			peliculas.remove(peliculaBorrar);
			this.vendedorService.save(vendedor);
			this.peliculaService.delete(peliculaBorrar);
			modelMap.addAttribute("message", "¡Producto eliminado!");
			return mostrarProductos(modelMap);
		} else {
			modelMap.addAttribute("message", "ERROR!");
			return "peliculas/PeliculasList";
		}
	}
	

	
	public String mostrarProductos(ModelMap modelMap) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetail = (UserDetails) auth.getPrincipal();
		String usuario = userDetail.getUsername();
		Vendedor vendedor = this.vendedorService.findVendedorByUsername(usuario);
		
		
		if(this.vendedorService.obtenerPeliculas(vendedor.getId())!=null) {
			List<Pelicula> peliculas = new ArrayList<>();
			peliculas.addAll(this.vendedorService.obtenerPeliculas(vendedor.getId()));
			modelMap.addAttribute("peliculas", peliculas);
		}
		if(this.vendedorService.obtenerVideojuegos(vendedor.getId())!=null) {
			List<Videojuego> videojuegos = new ArrayList<>();
			videojuegos.addAll(this.vendedorService.obtenerVideojuegos(vendedor.getId()));
			modelMap.addAttribute("videojuegos", videojuegos);
		}
		if(this.vendedorService.obtenerMerchandasings(vendedor.getId())!=null) {
			List<Merchandasing> merch = new ArrayList<>();
			merch.addAll(this.vendedorService.obtenerMerchandasings(vendedor.getId()));
			modelMap.addAttribute("merch", merch);
		}
		
		return "/productos/productosVendedor";
	}

}
