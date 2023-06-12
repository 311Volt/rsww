from locators import *
from elements import searchElement


class emailInputElement(searchElement):
    locator = HomePageLocators.emailInput


class passwordInputElement(searchElement):
    locator = HomePageLocators.passwordInput


class BasePage(object):
    def __init__(self, driver):
        self.driver = driver


class HomePage(BasePage):

    emailElement = emailInputElement()
    passwordElement = passwordInputElement()

    def __init__(self, driver):
        super().__init__(driver)
        self.btns = self.driver.find_elements(By.CSS_SELECTOR, ".btn-primary")

    
    def is_title_matches(self):
        return "Frontend" in self.driver.title
    
    def checkSignUp(self):
        return self.btns[0].accessible_name == "Sign Up"
    
    def checkLogin(self):
        return self.btns[0].accessible_name == "Login"
    
