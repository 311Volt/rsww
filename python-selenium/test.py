import unittest
from selenium import webdriver
from time import sleep
from page import *
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC


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
        print(driver.page_source)
        assert 


    def tearDown(self):
        self.driver.close()


if __name__ == "__main__":
    unittest.main() 
