package booker.BookingApp.selenium.tests;

import booker.BookingApp.selenium.page.ReservationsPage;
import org.junit.jupiter.api.DisplayName;
import org.testng.Assert;
import org.testng.annotations.Test;


public class CancellationTest extends TestBase {

    @Test
    @DisplayName("initial test, check if reservations exist")
    public void initialTest() throws InterruptedException {
        ReservationsPage reservationsPage = new ReservationsPage(driver);
        reservationsPage.start();
        Assert.assertNotEquals(reservationsPage.listSize(), 0);
    }

    @Test
    @DisplayName("should return false, try to click cancel on canceled reservation")
    public void CancelCanceledReservationTest() throws InterruptedException {
        ReservationsPage reservationsPage = new ReservationsPage(driver);
        int before = reservationsPage.canceledListSize();
        Assert.assertFalse(reservationsPage.cancelReservation(1));
        Assert.assertEquals(reservationsPage.canceledListSize(), before);
    }

    @Test
    @DisplayName("should return true, cancel reservation")
    public void CancelReservationPositiveTest() throws InterruptedException {
        ReservationsPage reservationsPage = new ReservationsPage(driver);
        int before = reservationsPage.canceledListSize();
        Assert.assertTrue(reservationsPage.cancelReservation(5));
        Assert.assertEquals(reservationsPage.canceledListSize(), before+1);
    }

    @Test
    @DisplayName("should return false, past reservation")
    public void PastReservationTest() throws InterruptedException {
        ReservationsPage reservationsPage = new ReservationsPage(driver);
        int before = reservationsPage.canceledListSize();
        Assert.assertFalse(reservationsPage.cancelReservation(0));
        Assert.assertEquals(reservationsPage.canceledListSize(), before);
    }

    @Test
    @DisplayName("should return false, cancellation time expired")
    public void CancellationTimeExpiredTest() throws InterruptedException {
        ReservationsPage reservationsPage = new ReservationsPage(driver);
        int before = reservationsPage.canceledListSize();
        Assert.assertTrue(reservationsPage.cancelReservation(6));
        Assert.assertEquals(reservationsPage.canceledListSize(), before);
    }

    @Test
    @DisplayName("should return alert text, try to click cancel on canceled reservation")
    public void alertText_CancelCanceledReservationTest() throws InterruptedException {
        ReservationsPage reservationsPage = new ReservationsPage(driver);
        int before = reservationsPage.canceledListSize();
        Assert.assertEquals(reservationsPage.getAlertMessageText(1), " ");
        Assert.assertEquals(reservationsPage.canceledListSize(), before);
    }

    @Test
    @DisplayName("should return alert text, click cancel when reservation is over")
    public void alertText_PastReservationTest() throws InterruptedException {
        ReservationsPage reservationsPage = new ReservationsPage(driver);
        int before = reservationsPage.canceledListSize();
        Assert.assertEquals(reservationsPage.getAlertMessageText(0), "this request has a past date so you can't cancel it");
        Assert.assertEquals(reservationsPage.canceledListSize(), before);
    }

    @Test
    @DisplayName("should return alert text, cancel reservation")
    public void alertText_CancelReservationPositiveTest() throws InterruptedException {
        ReservationsPage reservationsPage = new ReservationsPage(driver);
        int before = reservationsPage.canceledListSize();
        Assert.assertEquals(reservationsPage.getAlertMessageText(7), "Reservation is cancelled!");
        Assert.assertEquals(reservationsPage.canceledListSize(), before+1);
    }

    @Test
    @DisplayName("should return alert text, cancellation time expired")
    public void alertText_CancellationTimeExpiredTest() throws InterruptedException {
        ReservationsPage reservationsPage = new ReservationsPage(driver);
        int before = reservationsPage.canceledListSize();
        Assert.assertEquals(reservationsPage.getAlertMessageText(6), "You can not cancel reservation because deadline for " +
                "cancellation expired.\nUnfortunately you must pay for it.");
        Assert.assertEquals(reservationsPage.canceledListSize(), before);
    }


}
