#This is a piano bot which plays piono tiles automatically be passing the parameters of the piano tiles game 
# For better experience try on this site "https://www.primarygames.com/arcade/music/pianotiles/"
from pyautogui import*
import pyautogui
import time
import random
import keyboard
import win32api,win32con

# title 1 position X: 720 , Y : 900
# title 2 position X: 872 , Y : 900
# title 3 position X: 1017 , Y : 900
# title 4 position X:1174, Y : 900

def click(x,y):
    win32api.SetCursorPos((x,y))
    win32api.mouse_event(win32con.MOUSEEVENTF_LEFTDOWN,0,0)
    time.sleep(0.001)
    win32api.mouse_event(win32con.MOUSEEVENTF_LEFTUP,0,0)

while keyboard.is_pressed('q') == False:
        if pyautogui.pixel(338,750)[0] == 0 :
            click(338,750)

        if pyautogui.pixel(622,750)[0] == 0 :
            click(622,750)

        if pyautogui.pixel(894,750)[0] == 0 :
            click(894,750)

        if pyautogui.pixel(1161,750)[0] == 0 :
            click(1161,750)
