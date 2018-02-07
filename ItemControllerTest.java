package com.insags.mockito.tutorial;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import com.insags.mockito.tutorial.impl.ItemControllerImpl;

// [...]
public class ItemControllerTest {

	// [...]
	
	@Mock
	private ItemDao itemDao;
	
	@Before
	public void inicializarMocks(){
		MockitoAnnotations.initMocks(this);
	}
	
	@InjectMocks
	private ItemController itemController = new ItemControllerImpl();

	/**
	 * Método para probar que la obtención de todos los items se realiza de
	 * forma exitosa.
	 * 
	 * Resultado esperado:
	 * 
	 * - Se invoca el stub generado. 
	 * - Se ha recuperado un array con Peonza y Muñeca como elementos contenidos.
	 */
	@Test
	public void comprobarObtencionExitosaDeTodosLosItems() {
		String[] listaRespuesta = new String[2];
		listaRespuesta[0] = "Peonza";
		listaRespuesta[1] = "Muñeca";
		
		Mockito.when(itemDao.obtenerTodosLosItems()).thenReturn(listaRespuesta);
		List<String> respuesta = itemController.obtenerTodosLosItems();
		
		// Assert
		Mockito.verify(itemDao).obtenerTodosLosItems();
		Assert.assertNotNull(respuesta);
		Assert.assertTrue(respuesta.get(0).equals(listaRespuesta[0]));
		Assert.assertTrue(respuesta.get(1).equals(listaRespuesta[1]));
		
	}

	/**
	 * Método para probar que la obtención de todos los items se realiza de
	 * forma exitosa, pero haciendo uso de un objeto Answer.
	 * 
	 * Resultado esperado:
	 * 
	 * - Se invoca el stub generado. 
	 * - Se ha recuperado un array con Peonza y Muñeca como elementos contenidos.
	 */
	@Test
	public void comprobarObtencionExitosaDeTodosLosItemsHaciendoUsoDeAnswer() {
		Answer<String[]> listaRespuestaAnswer = new Answer<String[]>() {
			@Override
			public String[] answer(InvocationOnMock invocation) throws Throwable {
				return new String[] {"Peonza","Muñeca"};
			}
		};
		
		Mockito.when(itemDao.obtenerTodosLosItems()).thenAnswer(listaRespuestaAnswer);
		List<String> respuesta = itemController.obtenerTodosLosItems();
		
		// Assert
		Mockito.verify(itemDao).obtenerTodosLosItems();
		Assert.assertNotNull(respuesta);
		Assert.assertTrue(respuesta.get(0).equals("Peonza"));
		Assert.assertTrue(respuesta.get(1).equals("Muñeca"));
	}

	/**
	 * Método para probar el caso en el que la obtención de items no devuelve
	 * contenido, sólo la colección instanciada.
	 * 
	 * Resultado esperado:
	 * 
	 * - Se invoca el stub generado. 
	 * - Se ha recuperado una colección vacía.
	 */
	@Test
	public void comprobarObtencionVaciaDeTodosLosItems() {
		String[] listaRespuesta = new String[0];
		
		Mockito.when(itemDao.obtenerTodosLosItems()).thenReturn(listaRespuesta);
		List<String> respuesta = itemController.obtenerTodosLosItems();
		
		// Assert
		Mockito.verify(itemDao).obtenerTodosLosItems();
		Assert.assertNotNull(respuesta);
		Assert.assertTrue(respuesta.size() == 0);
	}

	/**
	 * Método para probar el caso en el que la obtención de items genera una
	 * excepción de tipo NullPointerException. El tipo de la excepción es
	 * indicado como parámetro de la anotación @Test
	 * 
	 * Resultado esperado:
	 * 
	 * - Se invoca el stub generado. 
	 * - Se ha lanzado la excepción de tipo NullPointerException.
	 */
	@Test(expected = NullPointerException.class)
	public void comprobarPorAnotacionLanzamientoExcepcionPorFaltaInyeccionDaoControlandoPorNotacion() throws Exception {
		List<String> respuesta = null;
		
		Mockito.when(itemDao.obtenerTodosLosItems()).thenThrow(NullPointerException.class);
		try {
			respuesta = itemController.obtenerTodosLosItems();
		} catch (Exception e) {
			// Assert
			Mockito.verify(itemDao).obtenerTodosLosItems();
			Assert.assertTrue(e instanceof NullPointerException);
			
			throw e;
		}
		fail("Nuca debe pasar por aqui si ha ido bien");
	}

	/**
	 * Método para probar el caso en el que la obtención de items genera una
	 * excepción de tipo NullPointerException. La prueba no parametriza la
	 * anotación @Test
	 * 
	 * Resultado esperado:
	 * 
	 * - Se invoca el stub generado. 
	 * - Se ha lanzado la excepción de tipo NullPointerException.
	 */
	@Test
	public void comprobarObtencionItemsErroneaPorLanzamientoExcepcionPorFaltaInyeccionDaoControlandoPorCodigo() {
		List<String> respuesta = null;
		
		Mockito.when(itemDao.obtenerTodosLosItems()).thenThrow(NullPointerException.class);
		try {
			respuesta = itemController.obtenerTodosLosItems();
		} catch (Exception e) {
			// Assert
			Mockito.verify(itemDao).obtenerTodosLosItems();
			Assert.assertTrue(e instanceof NullPointerException);			
		}
	}

	/**
	 * Método para probar el caso en el que la actualización de un item se lleva
	 * a cabo de forma exitosa.
	 * 
	 * Resultado esperado:
	 * 
	 * - Se invoca el stub generado. 
	 * - No se ha lanzado excepción alguna.
	 */
	@Test
	public void comprobarActualizacionExitosaDeItem() {
		String item = "item";
		Integer posicion = 1;
		
		Mockito.doNothing().when(itemDao).actualizarItem(Matchers.anyString(), Matchers.anyInt());
		try {
			itemController.actualizarItem(item, posicion);
		} catch (Exception e) {
			fail("No debe lanzar excepciones");
		}
		// Assert
		Mockito.verify(itemDao).actualizarItem(Matchers.anyString(), Matchers.anyInt());
	}

	/**
	 * Método para probar el caso en el que la actualización de un item provoca
	 * una excepción de tipo ArrayIndexOutOfBoundsException, la cual tiene
	 * asociada el mensaje "Excepción esperada". No se hace uso de la
	 * parametrización de la anotación @Test
	 * 
	 * Resultado esperado:
	 * 
	 * - Se invoca el stub generado. 
	 * - Se ha lanzado una excepción de tipo ArrayIndexOutOfBoundsException. 
	 * - El mensaje de la excepción es "Excepción esperada".
	 */
	@Test
	public void comprobarActualizacionFallidaPorExcederTamañoDelArray() {
		String item = "item";
		Integer posicion = 1;
		
		Mockito.doThrow(new ArrayIndexOutOfBoundsException("Excepción esperada")).when(itemDao).actualizarItem(Matchers.anyString(), Matchers.anyInt());
		try {
			itemController.actualizarItem(item, posicion);
		} catch (ArrayIndexOutOfBoundsException e) {
			// Assert
			Mockito.verify(itemDao).actualizarItem(Matchers.anyString(), Matchers.anyInt());
			Assert.assertEquals("Excepción esperada", e.getMessage());
		} catch (Exception e2) {
			fail("No puede ser otra excepción");
		}
	}

	/**
	 * Método para probar el caso en el que se realiza una actualización exitosa
	 * de un item, mostrando además por consola la cadena proporcionada, a la
	 * que se le han eliminado todos los espacios en blanco.
	 * 
	 * Resultado esperado:
	 * 
	 * - Se invoca el stub generado. 
	 * - Se ha mostrado por consola la cadena "UnoDosTresCuatroCinco".
	 */
	@Test
	public void comprobarActualizacionExitosaDeItemMostrandoEnConsolaSuValorEliminandoEspaciosEnBlancoUsandoAnswer() {
		// ARRANGE
		String item = " Uno Dos Tres Cuatro Cinco ";
		Integer posicion = 1;
		
		Answer<String> listaRespuestaAnswer = new Answer<String>() {
			@Override
			public String answer(InvocationOnMock invocation) throws Throwable {
				String item = invocation.getArgumentAt(0, String.class);
				System.out.println(item.replaceAll("\\s", ""));
				return item;
			}
		};
		
		Mockito.doAnswer(listaRespuestaAnswer).when(itemDao).actualizarItem(item, posicion);
		
		try {
			this.itemController.actualizarItem(item, posicion);
		} catch(Exception e) {
			fail("No debe retornar excepciones");
		}
		
		Mockito.verify(itemDao).actualizarItem(item, posicion);
	}

	/**
	 * Método para probar el caso en el que el reseteo de items se lleva a cabo
	 * de forma satisfactoria.
	 * 
	 * Resultado esperado:
	 * 
	 * - Se invoca el stub generado. 
	 * - No se ha lanzado excepción alguna. 
	 * - Comprobar que el número de actualizaciones llevas a cabo son 2.
	 */
	@Test
	public void comprobarReseteoExitosoDeItemsVerificandoNumeroEstrictoDeReseteos() {
		// ARRANGE
		String EMPTY_STRING = "";
		Integer[] posiciones = { 2, 3 };
		
		try {
			itemController.resetearItemsEspecificos(posiciones);
		}  catch (Exception e2) {
			fail("No puede ser otra excepción");
		}
		
		// Assert
		Mockito.verify(itemDao, Mockito.times(2)).actualizarItem(Matchers.anyString(), Matchers.anyInt());
	}

	/**
	 * Método para probar el caso en el que el listado de items se lleva a cabo
	 * de forma exitosa, comprobando que se han invocado los métodos en el orden
	 * y número esperado.
	 * 
	 * Resultado esperado:
	 * 
	 * - Se invoca el stub generado. 
	 * - No se ha lanzado excepción alguna. 
	 * - Se ha invocado el método obtenerTodosLosItems una sola vez. 
	 * - Se ha invocado el método actualizarItem tres veces.
	 */
	@Test
	public void comprobarListadoItemsParaReseteoConControlEstrictoIvocaciones() {
		// ARRANGE
		String[] items = { "uno", "dos", "tres" };
		
		Mockito.when(itemDao.obtenerTodosLosItems()).thenReturn(items);
		
		try {
			this.itemController.listaItemsParaReseteo();
		} catch(Exception e) {
			fail("No debe retornar excepciones");
		}
//		InOrder inOrder = 
		
	}

}
