package ule.edi.event;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.*;

import ule.edi.model.*;
import ule.edi.model.Configuration.Type;

public class EventArrayImplTests {
	
	private DateFormat dformat = null;
	private EventArrayImpl e;
	private EventArrayImpl e2;
	
	private Date parseLocalDate(String spec) throws ParseException {
        return dformat.parse(spec);
	}

	public EventArrayImplTests() {
		
		dformat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	}
	
	@Before
	public void testBefore() throws Exception{
	    e = new EventArrayImpl("The Fabulous Five", parseLocalDate("24/02/2018 17:00:00"), 110);
	    Byte i= 25;
	    e2= new EventArrayImpl("The Three", parseLocalDate("12/03/2019 18:00:00"), 2, 100.0, i);
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
	
	@Test
	public void getSeatOcupado() throws Exception{
		int pos= 10;
		
		Person person= new Person("23442040Z","Jon", 32);
		
		Seat seat= new Seat(e, pos, Type.NORMAL, person);
		
		e.sellSeat(pos, person, false);
		
		Assert.assertEquals( e.getSeat(pos).getHolder(), seat.getHolder());
	}
	
	// METODO GET SEAT EMPTY POSITION
	
	@Test
	public void getSeatEmptyPosition() throws Exception{
		Assert.assertNull( e.getSeat(10));
	}
	
	// METODO GET SEAT INVALID POSITION
	
	@Test
	public void getSeatInvalidPosition() throws Exception{
		Assert.assertNull( e.getSeat(1000));
		Assert.assertNull( e.getSeat(-3));
	}
	
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
	
	// METODO SELL GET NUMBER OF CHILDREN, 1 CHILD
	
	@Test
	public void getNumberOfAttendingChildrenOneChild() throws Exception{
		Person person= new Person("Nico","90233040A",9);	// 1 child
		e.sellSeat(12, person, false);
		Assert.assertEquals(e.getNumberOfAttendingChildren(), 1);
	}
	
	// METODO SELL GET NUMBER OF CHILDREN, NO CHILDREN
	
	@Test
	public void getNumberOfAttendingChildrenNoChildren() throws Exception{
		Person person= new Person("Nico","90233040A",40);	// >14 years
		e.sellSeat(12, person, false);
		Assert.assertEquals(e.getNumberOfAttendingChildren(), 0);
	}
	
	// METODO SELL GET NUMBER OF ADULTS, 1 ADULT, 1 ELDERLY
	
	@Test
	public void getNumberOfAttendingAdultsOneAdultOneElderly() throws Exception{
		Person person= new Person("Jon","90233040A",34);	// 1 adult
		Person personTwo= new Person("Tom","56287020A",72);	// 1 elderly
		e.sellSeat(12, person, false);
		e.sellSeat(13, personTwo, false);
		Assert.assertEquals(e.getNumberOfAttendingAdults(), 1);
	}
	
	// METODO SELL GET NUMBER OF ADULTS, NO ADULTS
	
	@Test
	public void getNumberOfAttendingAdultsNoAdults() throws Exception{
		Person person= new Person("Nico","90233040A",9);	// 1 child
		e.sellSeat(12, person, false);
		Assert.assertEquals(e.getNumberOfAttendingAdults(), 0);
	}
	
	// METODO SELL GET NUMBER OF ELDERLY, 1 ELDERLY, 1 ADULT
	
	@Test
	public void getNumberOfAttendingElderlyPeople() throws Exception{
		Person person= new Person("Albert","97030040Z",78);	// 1 elderly
		Person personTwo= new Person("Jon","12030384R",45);	// 1 adult
		e.sellSeat(12, person, false);
		e.sellSeat(13, personTwo, false);
		Assert.assertEquals(e.getNumberOfAttendingElderlyPeople(), 1);
	}
	
	// METODO GET PRICE, NORMAL PRICE 
	
	@Test
	public void getPriceNormal() throws Exception{
		int pos= 10;
		
		Person person= new Person("23442040Z","Jon", 32);
		
		Seat seat= new Seat(e, pos, Type.NORMAL, person);
		
		e.sellSeat(pos, person, false);
		
		Assert.assertEquals( e.getPrice(seat), 100.0, 0.001);
	}

	// METODO GET PRICE, ADVANCE PRICE
	
	@Test
	public void getPriceDiscount() throws Exception{
		int pos= 10;
		
		Person person= new Person("23442040Z","Jon", 32);
		
		Seat seat= new Seat(e, pos, Type.ADVANCE_SALE, person);
		
		e.sellSeat(pos, person, true);
		
		Assert.assertEquals( e.getPrice(seat), 75.0, 0.001);
	}
	
	// METODO GET PRICE, SEAT NULL
	
	@Test
	public void getPriceSeatNull() throws Exception{
		Seat seat= null;
		
		Assert.assertEquals( e.getPrice(seat), 0.0, 0.001);
	}
	
	// METODO GET PRICE, ADVANCE PRICE
	
	@Test
	public void getPriceSeatDiferentFromThis() throws Exception{
		int pos= 10;
		
		Person person= new Person("23442040Z","Jon", 32);
		
		Seat seat= new Seat(e2, pos, Type.ADVANCE_SALE, person);
		
		Assert.assertEquals( e.getPrice(seat), 0.0, 0.001);
	}
	
	// METODO IS ADVANCE SALE, ADVANCE SALE PERSON
	
	@Test
	public void isAdvanceSaleAdvanceSale() throws Exception{
		Person person= new Person("23442040Z","Jon", 32);
		
		e.sellSeat(10, person, true);
		
		Assert.assertTrue( e.isAdvanceSale(person));
	}
	
	// METODO IS ADVANCE SALE, NORMAL SALE PERSON

	@Test
	public void isAdvanceSaleNormalSale() throws Exception{
		Person person= new Person("23442040Z","Jon", 32);
		
		e.sellSeat(10, person, false);
		
		Assert.assertFalse( e.isAdvanceSale(person));
	}
	
	// METODO IS ADVANCE SALE, A PERSON WITHOUT SEATS

	@Test
	public void isAdvanceSalePersonWithoutSeats() throws Exception{
		Person person= new Person("23442040Z","Jon", 32); // Person without seats
		Person personTwo= new Person("13278310I","Julio", 22); // Person with all seats bought
		e2.sellSeat(1, personTwo, false);
		e2.sellSeat(2, personTwo, false);
		Assert.assertFalse( e2.isAdvanceSale(person));
	}
	
	// METODO GET COLLECTION EVENT
	
	@Test
	public void getCollectionEvent() throws Exception{
		Person person= new Person("23442040Z","Jon", 32);
		
		e.sellSeat(10, person, true);
		
		Assert.assertEquals( e.getCollectionEvent(), 75.0, 0.001);
	}
	
	// METODO GET POS PERSON, 1 PERSON POSITION 10
	
	@Test
	public void getPosPerson() throws Exception{
		Person person= new Person("23442040Z","Jon", 32);
		
		e.sellSeat(10, person, true);
		
		Assert.assertEquals( e.getPosPerson(person), 10);
	}
	
	// METODO GET POS PERSON, A PERSON WITHOUT SEATS

	@Test
	public void getPosPersonWithoutSeats() throws Exception{
		Person person= new Person("23442040Z","Jon", 32); // Person without seats
		Person personTwo= new Person("13278310I","Julio", 22); // Person with all seats bought
		e2.sellSeat(1, personTwo, false);
		e2.sellSeat(2, personTwo, false);
		Assert.assertEquals( e2.getPosPerson(person), -1); //it resturns -1 when the person is not in the event
	}
	
	//METODO GET AVAILABLE SEATS LIST, ALL AVAILABLE
	
	@Test
	public void getAvailableSeatsListAllAvailable() throws Exception{
		List<Integer> availableSeats = new ArrayList<>(2);
		availableSeats.add(1);
		availableSeats.add(2);
		Assert.assertEquals(availableSeats, e2.getAvailableSeatsList());
	}
	
	//METODO GET AVAILABLE SEATS LIST, ONE NOT AVAILABLE
	
	@Test
	public void getAvailableSeatsListOneNotAvailable() throws Exception{
		List<Integer> availableSeats = new ArrayList<>(2);
		availableSeats.add(1);
		e2.sellSeat(2, new Person("13278310I","Julio", 22), false);
		Assert.assertEquals(availableSeats, e2.getAvailableSeatsList());
	}
	
	//METODO GET ADVANCE SALE SEATS LIST, 1 ADVANCE THE OTHER NOT
	
	@Test
	public void getAdvanceSaleSeats() throws Exception{
		List<Integer> advanceSaleSeats = new ArrayList<>(2);
		e2.sellSeat(1, new Person("13278310I","Julio", 22), true);
		e2.sellSeat(2, new Person("13278310I","Julio", 22), false);
		advanceSaleSeats.add(1);
		Assert.assertEquals(advanceSaleSeats, e2.getAdvanceSaleSeatsList());
	}
	
	//METODO GET ADVANCE SALE SEATS LIST, 1 ADVANCE THE OTHER AVAILABLE
	
	@Test
	public void getAdvanceSaleSeatsOneAvailable() throws Exception{
		List<Integer> advanceSaleSeats = new ArrayList<>(2);
		e2.sellSeat(1, new Person("13278310I","Julio", 22), true);
		advanceSaleSeats.add(1);
		Assert.assertEquals(advanceSaleSeats, e2.getAdvanceSaleSeatsList());
	}
	
	//METODO GET MAX NUMBER CONSECUTIVE SEATS
	
	@Test
	public void getMaxNumberConsecutiveSeats() throws Exception{
		e.sellSeat(50, new Person("13278310I","Julio", 22), true);
		
		Assert.assertEquals(e.getMaxNumberConsecutiveSeats(),60);
	}
}
