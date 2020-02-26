package ule.edi.event;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.*;

import ule.edi.model.*;
import ule.edi.model.Configuration.Type;

public class EventArrayImplTests {
	
	private DateFormat dformat = null;
	private EventArrayImpl e;
	
	private Date parseLocalDate(String spec) throws ParseException {
        return dformat.parse(spec);
	}

	public EventArrayImplTests() {
		
		dformat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	}
	
	@Before
	public void testBefore() throws Exception{
	    e = new EventArrayImpl("The Fabulous Five", parseLocalDate("24/02/2018 17:00:00"), 110);

	}
	
	@Test
	public void testEventoVacio() throws Exception {
		
	    Assert.assertTrue(e.getNumberOfAvailableSeats()==110);
	    Assert.assertEquals(e.getNumberOfAvailableSeats(), 110);
	    Assert.assertEquals(e.getNumberOfAttendingAdults(), 0);
	}
	
	@Test
	public void testSellSeat1Adult() throws Exception{
		
	    Assert.assertEquals(e.getNumberOfAttendingAdults(), 0);
		Assert.assertTrue(e.sellSeat(1, new Person("Alice","10203040A", 34),false));	//venta normal
	    Assert.assertEquals(e.getNumberOfAttendingAdults(), 1);  
	    Assert.assertEquals(e.getNumberOfNormalSaleSeats(), 1);
	  
	}

	@Test
	public void testgetCollection() throws Exception{
		Event  ep = new EventArrayImpl("The Fabulous Five", parseLocalDate("24/02/2018 17:00:00"), 4);
		Assert.assertEquals(ep.sellSeat(1, new Person("Paul", "10293040A", 10), true),true);
		Assert.assertTrue(ep.getCollectionEvent()==75);					
	}
	
	// TODO EL RESTO DE MÉTODOS DE TESTS NECESARIOS PARA LA COMPLETA COMPROBACIÓN DEL BUEN FUNCIONAMIENTO DE TODO EL CÓDIGO IMPLEMENTADO
	
	// METODO SELL SEAT
	
	@Test
	public void testSellSeatHigherPos() throws Exception {
		Assert.assertFalse(e.sellSeat(2000, new Person("Peter","10203040A", 34),false));
	}
	
	@Test
	public void testSellSeatLowerPos() throws Exception {
		Assert.assertFalse(e.sellSeat(-3, new Person("Peter","10203040A", 34),false));
	}

	@Test
	public void testSellSeat() throws Exception {
		Assert.assertTrue(e.sellSeat(10, new Person("Peter","10203040A", 34),false));
	}

	// METODO GET NAME
	
	@Test
	public void testGetName() throws Exception{
		Assert.assertEquals( e.getName(), "The Fabulous Five");
	}
	
	// METODO GET DATE EVENT
	
	@Test
	public void testGetDateEvent() throws Exception{
		Assert.assertEquals( e.getDateEvent(), parseLocalDate("24/02/2018 17:00:00"));
	}
	
	// METODO GET PRICE

	@Test
	public void testGetPrice() throws Exception{
		Assert.assertEquals( e.getPrice(), Configuration.DEFAULT_PRICE);
	}
	
	// METODO GET DISCOUNT ADVANCE SALE

	@Test
	public void testGetDiscountAdvanceSale() throws Exception{
		Assert.assertEquals( e.getDiscountAdvanceSale(), Configuration.DEFAULT_DISCOUNT);
	}
	
	// METODO GET NUMBER OF SOLD SEATS

	@Test
	public void testGetNumberOfSoldSeats() throws Exception{
		e.sellSeat(2, new Person("Marcos","10203040A", 21), false);
		Assert.assertEquals( e.getNumberOfSoldSeats(), 1);
	}
	
	// METODO GET NUMBER OF NORMAL SOLD SEATS 1 NORMAL SEAT

	@Test
	public void testgetNumberOfNormalSaleSeatsNormal() throws Exception{
		e.sellSeat(3, new Person("Alfred","10203040A", 32), false);
		Assert.assertEquals( e.getNumberOfNormalSaleSeats(), 1);
	}
	
	// METODO GET NUMBER OF NORMAL SOLD SEATS 1 ADVANCE SEAT

	@Test
	public void testgetNumberOfNormalSaleSeatsAdvance() throws Exception{
		e.sellSeat(3, new Person("Marcos","10203040A", 32), true);
		Assert.assertEquals( e.getNumberOfNormalSaleSeats(), 0);
	}

	// METODO GET NUMBER OF ADVANCE SOLD SEATS 1 ADVANCE SEAT

	@Test
	public void getNumberOfAdvanceSaleSeatsAdvance() throws Exception{
		e.sellSeat(3, new Person("Jon","10203040A", 32), true);
		Assert.assertEquals( e.getNumberOfAdvanceSaleSeats(), 1);
	}
	
	// METODO GET NUMBER OF ADVANCE SOLD SEATS 1 NORMAL SEAT

	@Test
	public void getNumberOfAdvanceSaleSeatsNormal() throws Exception{
		e.sellSeat(3, new Person("Marcos","10203040A", 32), false);
		Assert.assertEquals( e.getNumberOfAdvanceSaleSeats(), 0);
	}
	
	// METODO GET NUMBER OF SEATS SOLD AND AVAILABLE

	@Test
	public void getNumberOfSeats() throws Exception{
		Assert.assertEquals( e.getNumberOfSeats(), 110);
	}
	
	// METODO GET NUMBER OF AVAILABLE SEATS WITH ALL AVAILABLE

	@Test
	public void getNumberOfAvailableSeatsAllAvailable() throws Exception{
		Assert.assertEquals( e.getNumberOfAvailableSeats(), 110);
	}
	
	// METODO GET NUMBER OF AVAILABLE SEATS 1 NOT AVAILABLE

	@Test
	public void getNumberOfAvailableSeatsOneNotAvailable() throws Exception{
		e.sellSeat(90, new Person("Jon","10203040A", 32), false);
		Assert.assertEquals( e.getNumberOfAvailableSeats(), 109);
	}
	
	// METODO GET SEAT CON SITIO OCUPADO
	
	/*@Test
	public void getSeatOcupado() throws Exception{
		int pos= 10;
		
		Person person= new Person("23442040Z","Jon", 32);
		
		Seat seat= new Seat(e, pos, Type.NORMAL, person);
		
		e.sellSeat(pos, person, false);
		
		Assert.assertEquals( e.getSeat(pos), seat);
	}*/
	
	// METODO REFOUND SEAT SUSTITUIR PERSONA

	@Test
	public void refoundSeat() throws Exception{
		Person person= new Person("Robert","10203040A",32);
		e.sellSeat(12, person, false);
		Assert.assertTrue( e.refundSeat(12).equals(person));
		Assert.assertNull(e.refundSeat(12));
	}
	
	// METODO REFOUND SEAT, SEAT NULL

	@Test
	public void refoundSeatSeatNull() throws Exception{
		Assert.assertNull(e.refundSeat(12));
	}
	
	// METODO REFOUND SEAT, INVALID POINTER

	@Test
	public void refoundSeatSeatInvalidPointer0() throws Exception{
		Assert.assertNull(e.refundSeat(0));
	}
	
	// METODO REFOUND SEAT, INVALID POINTER
	
	@Test
	public void refoundSeatSeatInvalidPointer() throws Exception{
		Assert.assertNull(e.refundSeat(1000));
	}
	
	// METODO SELL SEAT TRUE
	
	@Test
	public void sellSeatTrue() throws Exception{
		Person person= new Person("Robert","10203040A",32);
		Assert.assertTrue(e.sellSeat(12, person, false));
	}
	
	// METODO SELL SEAT FALSE
	
	@Test
	public void sellSeatFalse() throws Exception{
		Person person= new Person("Robert","10203040A",32);
		e.sellSeat(12, person, false);
		Assert.assertFalse(e.sellSeat(12, person, false));
	}
	
	
}
