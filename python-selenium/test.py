import unittest
from selenium import webdriver
from time import sleep
from page import *
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from datetime import date, datetime, timedelta
from selenium.webdriver.common.keys import Keys


URI = "http://localhost:4200/"


class rswwTester(unittest.TestCase):

    def setUp(self):
        self.driver = webdriver.Chrome()
        self.driver.get(URI)

    
    def loginFunc(self):
        homePage = HomePage(self.driver)
        if not homePage.checkLogin():
            homePage.btns[1].click()
        homePage.emailElement = "testAccount@test.net"
        homePage.passwordElement = "testPassword"
        sleep(1)
        homePage.btns[0].click()
        sleep(2)


    def testAccountCreation(self):
        homePage = HomePage(self.driver)
        if not homePage.checkSignUp():
            homePage.btns[1].click()
            self.driver.implicitly_wait(2)

        homePage.emailElement = "testAccount@test.net"
        homePage.passwordElement = "testPassword"
        sleep(1)

        self.driver.implicitly_wait(2)
        homePage.btns[0].click()
        sleep(1)
        assert self.driver.find_element(By.CSS_SELECTOR, ".text-muted")


    def testLogin(self):
        self.loginFunc()
        nav = "Logout" in self.driver.page_source
        assert nav


    def testFilterCountry(self):
        self.loginFunc()
        filterElement = self.driver.find_elements(By.CSS_SELECTOR, ".mat-input-element")[0]
        filterElement.clear()
        filterElement.send_keys("Grecja")
        sleep(2)
        td_element = self.driver.find_elements(By.CSS_SELECTOR, ".mat-cell")[0]
        afterFilter = td_element.accessible_name
        assert  afterFilter == "Grecja"


    def testDateFilter(self):
        self.loginFunc()
        tourDates = self.driver.find_elements(By.CSS_SELECTOR, ".cdk-column-departure")
        tourDateFilter = tourDates[2].accessible_name
        filterElement = self.driver.find_elements(By.CSS_SELECTOR, ".mat-input-element")[3]
        filterElement.clear()
        temp = tourDateFilter.replace(".","/")
        format = "%m/%d/%Y"
        dateObj = datetime.strptime(temp, format)
        dateObj = dateObj + timedelta(days=1)
        temp = dateObj.strftime(format)
        filterElement.clear()
        filterElement.send_keys(temp)
        sleep(3)

        testExpression = self.driver.find_element(By.CSS_SELECTOR, ".mat-paginator-range-label").text

        if(testExpression == "0 of 0"):
            assert True
        else:
            tourDates = self.driver.find_elements(By.CSS_SELECTOR, ".mat-column-departure")
            tourDateFilter = tourDates[1].accessible_name
            dateObj = dateObj + timedelta(days=-1)
            temp = dateObj.strftime(format)
        assert not (tourDateFilter == temp) or testExpression == "0 of 0"
        

    def testPurhcase(self):
        self.testLogin()
        btn = self.driver.find_elements(By.CSS_SELECTOR, ".mat-focus-indicator")[5]
        btn.click()
        sleep(3)
        btn = self.driver.find_elements(By.CSS_SELECTOR, ".mat-button-base")[0]
        btn.click()
        numberOfOffers = self.driver.find_element(By.NAME, "numberOfOffers")
        numberOfOffers.clear()
        numberOfOffers.send_keys("2")
        singleRoomsNumber = self.driver.find_element(By.NAME, "singleRoomsNumber")
        singleRoomsNumber.clear()
        singleRoomsNumber.send_keys("1")
        doubleRoomsNumber = self.driver.find_element(By.NAME, "doubleRoomsNumber")
        doubleRoomsNumber.clear()
        doubleRoomsNumber.send_keys("1")
        ageRange1NumberOfPeople = self.driver.find_element(By.NAME, "ageRange1NumberOfPeople")
        ageRange1NumberOfPeople.clear()
        ageRange1NumberOfPeople.send_keys("1")
        ageRange2NumberOfPeople = self.driver.find_element(By.NAME, "ageRange2NumberOfPeople")
        ageRange2NumberOfPeople.clear()
        ageRange2NumberOfPeople.send_keys("1")
        sleep(5)
        btn = self.driver.find_element(By.CSS_SELECTOR, ".mat-stroked-button")
        btn.send_keys(Keys.ENTER)
        sleep(3)
        assert "Logout" in self.driver.page_source



    def tearDown(self):
        self.driver.close()


if __name__ == "__main__":
    unittest.main() 
