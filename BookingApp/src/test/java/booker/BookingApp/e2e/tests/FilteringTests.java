package booker.BookingApp.e2e.tests;

import booker.BookingApp.e2e.pages.AccommodationListingPage;
import booker.BookingApp.e2e.pages.HomePage;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;

public class FilteringTests extends TestBase{

    @Test
    public void amenityFilterNothingFound() throws InterruptedException {
        String url = "http://localhost:4200/search/2024-03-23/2024-03-27/London/2";
        AccommodationListingPage listingPage = new AccommodationListingPage(driver, url);
        ArrayList<String> names = (ArrayList<String>) listingPage.getAllAccommodationNames();
        Assert.assertTrue(names.size()==3);

        listingPage.clickOnAmenity(0);
        listingPage.clickOnSearch();
        Thread.sleep(200);

        names = (ArrayList<String>) listingPage.getAllAccommodationNames();
        Assert.assertTrue(names.size()==0);
    }

    @Test
    public void amenityFilterOneFound() throws InterruptedException {
        String url = "http://localhost:4200/search/2024-03-23/2024-03-27/London/2";
        AccommodationListingPage listingPage = new AccommodationListingPage(driver, url);
        ArrayList<String> names = (ArrayList<String>) listingPage.getAllAccommodationNames();
        Assert.assertTrue(names.size()==3);

        listingPage.clickOnAmenity(2);
        listingPage.clickOnSearch();
        Thread.sleep(200);

        names = (ArrayList<String>) listingPage.getAllAccommodationNames();
        Assert.assertTrue(names.size()==1);
        Assert.assertTrue(names.get(0).equals("Villa Relaxation"));
    }

    @Test
    public void typeFilterNothingFound() throws InterruptedException {
        String url = "http://localhost:4200/search/2024-03-23/2024-03-27/London/2";
        AccommodationListingPage listingPage = new AccommodationListingPage(driver, url);
        ArrayList<String> names = (ArrayList<String>) listingPage.getAllAccommodationNames();
        Assert.assertTrue(names.size()==3);

        listingPage.clickOnType(1);
        listingPage.clickOnSearch();
        Thread.sleep(200);

        names = (ArrayList<String>) listingPage.getAllAccommodationNames();
        Assert.assertTrue(names.size()==0);
    }

    @Test
    public void typeFilterOneFound() throws InterruptedException {
        String url = "http://localhost:4200/search/2024-03-23/2024-03-27/London/2";
        AccommodationListingPage listingPage = new AccommodationListingPage(driver, url);
        ArrayList<String> names = (ArrayList<String>) listingPage.getAllAccommodationNames();
        Assert.assertTrue(names.size()==3);

        listingPage.clickOnType(2);
        listingPage.clickOnSearch();
        Thread.sleep(200);

        names = (ArrayList<String>) listingPage.getAllAccommodationNames();
        Assert.assertTrue(names.size()==1);
        Assert.assertTrue(names.get(0).equals("Example apartment 2"));
    }

    @Test
    public void priceFilterNothingFound() throws InterruptedException {
        String url = "http://localhost:4200/search/2024-03-23/2024-03-27/London/2";
        AccommodationListingPage listingPage = new AccommodationListingPage(driver, url);
        ArrayList<String> names = (ArrayList<String>) listingPage.getAllAccommodationNames();
        Assert.assertTrue(names.size()==3);

        listingPage.enterMinPrice("500");
        listingPage.enterMaxPrice("500");
        listingPage.clickOnSearch();
        Thread.sleep(200);

        names = (ArrayList<String>) listingPage.getAllAccommodationNames();
        Assert.assertTrue(names.size()==0);
    }

    @Test
    public void priceFilterOneFound() throws InterruptedException {
        String url = "http://localhost:4200/search/2024-03-23/2024-03-27/London/2";
        AccommodationListingPage listingPage = new AccommodationListingPage(driver, url);
        ArrayList<String> names = (ArrayList<String>) listingPage.getAllAccommodationNames();
        Assert.assertTrue(names.size()==3);

        listingPage.enterMinPrice("500");
        listingPage.clickOnSearch();
        Thread.sleep(200);

        names = (ArrayList<String>) listingPage.getAllAccommodationNames();
        Assert.assertTrue(names.size()==1);
        Assert.assertTrue(names.get(0).equals("Villa Relaxation"));
    }

    @Test
    public void amenityAndTypeFilterNothingFound() throws InterruptedException {
        String url = "http://localhost:4200/search/2024-03-23/2024-03-27/London/2";
        AccommodationListingPage listingPage = new AccommodationListingPage(driver, url);
        ArrayList<String> names = (ArrayList<String>) listingPage.getAllAccommodationNames();
        Assert.assertTrue(names.size()==3);

        listingPage.clickOnAmenity(2);      //amenity that finds one
        listingPage.clickOnType(2);         //type that finds one
        listingPage.clickOnSearch();              //together they don't find anything
        Thread.sleep(200);

        names = (ArrayList<String>) listingPage.getAllAccommodationNames();
        Assert.assertTrue(names.size()==0);
    }

    @Test
    public void amenityAndTypeFilterOneFound() throws InterruptedException {
        String url = "http://localhost:4200/search/2024-03-23/2024-03-27/London/2";
        AccommodationListingPage listingPage = new AccommodationListingPage(driver, url);
        ArrayList<String> names = (ArrayList<String>) listingPage.getAllAccommodationNames();
        Assert.assertTrue(names.size()==3);

        listingPage.clickOnAmenity(3);      //amenity that finds two
        listingPage.clickOnType(2);         //type that finds one
        listingPage.clickOnSearch();              //together they find one
        Thread.sleep(200);

        names = (ArrayList<String>) listingPage.getAllAccommodationNames();
        Assert.assertTrue(names.size()==1);
        Assert.assertTrue(names.get(0).equals("Example apartment 2"));
    }

    @Test
    public void amenityAndPriceFilterNothingFound() throws InterruptedException {
        String url = "http://localhost:4200/search/2024-03-23/2024-03-27/London/2";
        AccommodationListingPage listingPage = new AccommodationListingPage(driver, url);
        ArrayList<String> names = (ArrayList<String>) listingPage.getAllAccommodationNames();
        Assert.assertTrue(names.size()==3);

        listingPage.clickOnAmenity(3);      //amenity that finds one
        listingPage.enterMinPrice("500");         //type that finds one
        listingPage.clickOnSearch();              //together they don't find anything
        Thread.sleep(200);

        names = (ArrayList<String>) listingPage.getAllAccommodationNames();
        Assert.assertTrue(names.size()==0);
    }

    @Test
    public void amenityAndPriceFilterOneFound() throws InterruptedException {
        String url = "http://localhost:4200/search/2024-03-23/2024-03-27/London/2";
        AccommodationListingPage listingPage = new AccommodationListingPage(driver, url);
        ArrayList<String> names = (ArrayList<String>) listingPage.getAllAccommodationNames();
        Assert.assertTrue(names.size()==3);

        listingPage.clickOnAmenity(2);      //amenity that finds one
        listingPage.enterMinPrice("500");         //price that finds one
        listingPage.clickOnSearch();              //together they find one
        Thread.sleep(200);

        names = (ArrayList<String>) listingPage.getAllAccommodationNames();
        Assert.assertTrue(names.size()==1);
        Assert.assertTrue(names.get(0).equals("Villa Relaxation"));
    }

    @Test
    public void typeAndPriceFilterNothingFound() throws InterruptedException {
        String url = "http://localhost:4200/search/2024-03-23/2024-03-27/London/2";
        AccommodationListingPage listingPage = new AccommodationListingPage(driver, url);
        ArrayList<String> names = (ArrayList<String>) listingPage.getAllAccommodationNames();
        Assert.assertTrue(names.size()==3);

        listingPage.clickOnType(2);         //type that finds one
        listingPage.enterMinPrice("500");         //price that finds one
        listingPage.clickOnSearch();              //together they don't find anything
        Thread.sleep(200);

        names = (ArrayList<String>) listingPage.getAllAccommodationNames();
        Assert.assertTrue(names.size()==0);
    }

    @Test
    public void typeAndPriceFilterOneFound() throws InterruptedException {
        String url = "http://localhost:4200/search/2024-03-23/2024-03-27/London/2";
        AccommodationListingPage listingPage = new AccommodationListingPage(driver, url);
        ArrayList<String> names = (ArrayList<String>) listingPage.getAllAccommodationNames();
        Assert.assertTrue(names.size()==3);

        listingPage.clickOnType(2);      //type that finds one
        listingPage.enterMinPrice("300");         //price that finds two
        listingPage.clickOnSearch();              //together they find one
        Thread.sleep(200);

        names = (ArrayList<String>) listingPage.getAllAccommodationNames();
        Assert.assertTrue(names.size()==1);
        Assert.assertTrue(names.get(0).equals("Example apartment 2"));
    }

    @Test
    public void allFiltersNothingFound() throws InterruptedException {
        String url = "http://localhost:4200/search/2024-03-23/2024-03-27/London/2";
        AccommodationListingPage listingPage = new AccommodationListingPage(driver, url);
        ArrayList<String> names = (ArrayList<String>) listingPage.getAllAccommodationNames();
        Assert.assertTrue(names.size()==3);

        listingPage.clickOnAmenity(3);      //amenity that finds two
        listingPage.clickOnType(2);         //type that finds one
        //together amenity and type find one
        listingPage.enterMaxPrice("300");
        listingPage.clickOnSearch();
        Thread.sleep(200);

        names = (ArrayList<String>) listingPage.getAllAccommodationNames();
        Assert.assertTrue(names.size()==0);
    }

    @Test
    public void allFiltersFoundOne() throws InterruptedException {
        String url = "http://localhost:4200/search/2024-03-23/2024-03-27/London/2";
        AccommodationListingPage listingPage = new AccommodationListingPage(driver, url);
        ArrayList<String> names = (ArrayList<String>) listingPage.getAllAccommodationNames();
        Assert.assertTrue(names.size()==3);

        listingPage.clickOnAmenity(3);      //amenity that finds two
        listingPage.clickOnType(2);         //type that finds one
        //together amenity and type find one
        listingPage.enterMinPrice("300");
        listingPage.clickOnSearch();
        Thread.sleep(200);

        names = (ArrayList<String>) listingPage.getAllAccommodationNames();
        Assert.assertTrue(names.size()==1);
        Assert.assertTrue(names.get(0).equals("Example apartment 2"));
    }
}
