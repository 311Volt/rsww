import unittest
from selenium import webdriver
from time import sleep
from page import *
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from datetime import date, datetime, timedelta


URI = "http://localhost:4200/"

driver = webdriver.Chrome()



class rswwTester(unittest.TestCase):

    def setUp(self):
        self.driver = webdriver.Chrome()
        self.driver.get(URI)


    def testAccountCreation(self):
        homePage = HomePage(self.driver)
        if not homePage.checkSignUp():
            homePage.btns[1].click()
            self.driver.implicitly_wait(2)

        homePage.emailElement = "testAccount@test.net"
        homePage.passwordElement = "testPassword"

        self.driver.implicitly_wait(2)
        homePage.btns[0].click()
        assert self.driver.find_element(By.CSS_SELECTOR, ".text-muted")


    def testLogin(self):
        homePage = HomePage(self.driver)
        if not homePage.checkLogin():
            homePage.btns[1].click()
        homePage.emailElement = "testAccount@test.net"
        homePage.passwordElement = "testPassword"
        homePage.btns[0].click()
        self.driver.implicitly_wait(10)
        nav = "Logout" in self.driver.page_source
        assert nav


    def testFilterCountry(self):
        self.testLogin()
        td_element = self.driver.find_elements(By.CSS_SELECTOR, ".mat-cell")[0]
        beforeFilter = td_element.accessible_name
        filterElement = self.driver.find_elements(By.CSS_SELECTOR, ".mat-input-element")[0]
        filterElement.clear()
        filterElement.send_keys("Grecja")
        td_element = self.driver.find_elements(By.CSS_SELECTOR, ".mat-cell")[0]
        afterFilter = td_element.accessible_name
        assert not (afterFilter == beforeFilter)


    def testDateFilter(self):
        self.testLogin()
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

        testExpression = self.driver.find_element(By.CSS_SELECTOR, ".mat-paginator-range-label").text

        if(testExpression == "0 of 0"):
            assert True
        else:
            tourDates = self.driver.find_elements(By.CSS_SELECTOR, ".cdk-column-departure")
            tourDateFilter = tourDates[2].accessible_name
            dateObj = dateObj + timedelta(days=-1)
            temp = dateObj.strftime(format)
        assert not (tourDateFilter == temp) or testExpression == "0 of 0"
        

    def tearDown(self):
        self.driver.close()


if __name__ == "__main__":
    unittest.main() 
