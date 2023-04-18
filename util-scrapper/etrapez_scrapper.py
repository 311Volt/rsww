import urllib.request
import lxml.html as lh
import lxml.etree as et
import xml.dom.minidom as md
import hashlib
import os.path
import gzip
import json
import csv
import time
import sys
import copy

def url_cache_path(url):
    urlHash = hashlib.sha1(url.encode('utf-8')).hexdigest()
    return "httpcache/"+urlHash+".gz"

def is_url_cached(url):
    return os.path.exists(url_cache_path(url))

def get_resource_from_cache(url):
    if is_url_cached(url):
        with gzip.open(url_cache_path(url), "r") as file:
            return file.read()
    return None

def cache_webpage_content(url, content):
    with gzip.open(url_cache_path(url), "w") as file:
        file.write(content)
    pass        

def get_resource(url):
    if is_url_cached(url):
        return get_resource_from_cache(url)
    response = urllib.request.urlopen(url)
    content = response.read()
    cache_webpage_content(url, content)
    return content

def get_webpage_tree(url):
    content = get_resource(url).decode('utf-8')
    return lh.fromstring(content)

class ProgressCounter:
    def __init__(self, numTasks):
        self.counter = 0
        self.numTasks = numTasks
        self.progressStr = ""

    def print_progress(self):
        print("\b" * len(self.progressStr), end='', flush=True)
        self.progressStr = "(%d/%d)" % (self.counter, self.numTasks)
        print(self.progressStr, end='', flush=True)
        self.counter += 1

    def end(self):
        self.print_progress()
        print("")

def register_category(cat_dict,  cat_csv_dict, cat_name):
    if cat_name in cat_dict:
        return cat_dict[cat_name]

    cat_id = str(len(cat_dict)+3)
    cat_dict[cat_name] = cat_id
    csv_entry = {"id": cat_id, "name": cat_name}
    if i > 1:
        csv_entry["parent_id"] = str(cat_dict[catNodes[i-1].text])
    cat_csv_dict.append(csv_entry)
    return cat_id

def get_html_node_source(node, ignore_elements=[]):
    ret = ""
    node1 = copy.deepcopy(node)
    for elem_name in ignore_elements:
        for r_node in node1.xpath(".//"+elem_name):
            r_node.find("..").remove(r_node)
    for child in node1:
        ret += lh.tostring(child, encoding="unicode")
    return ret


MAIN_URL = "https://etrapez.pl/kursy"

os.makedirs("httpcache", exist_ok=True)
os.makedirs("output", exist_ok=True)
os.makedirs("output/images", exist_ok=True)


print("Fetching " + MAIN_URL + "...")
tree0 = get_webpage_tree(MAIN_URL)

pageLinks = tree0.xpath("//a[contains(@class, 'page-numbers')]")
pageURLs = set([a.get("href") for a in pageLinks])
pageURLs.add(MAIN_URL)
productURLs = []
products = []
fetchResult = []

progCounter = ProgressCounter(len(pageURLs))
print("Processing pages... ", end='')
for url in pageURLs:
    progCounter.print_progress()
    pageTree = get_webpage_tree(url)
    productLinks = pageTree.xpath("//a[contains(@class,'woocommerce-LoopProduct-link')]")
    productURLs.extend([a.get("href") for a in productLinks])
progCounter.end()

categories = dict()
categoriescsv = []

progCounter = ProgressCounter(len(productURLs))
print("Processing product pages... ", end='')
for url in productURLs:
    progCounter.print_progress()
    pageTree = get_webpage_tree(url)

    product = dict()

    titleNode = pageTree.xpath("//h1[contains(@class,'product_title entry-title')]")
    product["name"] = titleNode[0].text

    shortDescNode = pageTree.xpath("//div[contains(@class,'woocommerce-product-details__short-description')]")
    product["short_desc"] = get_html_node_source(shortDescNode[0], ignore_elements=["img", "iframe"])

    longDescNode = pageTree.xpath("//div[contains(@id,'tab-description')]")
    product["desc"] = get_html_node_source(longDescNode[0], ignore_elements=["img", "iframe", "p[contains(@class,'product')]"])

    catNodes = pageTree.xpath("//nav[contains(@class, 'woocommerce-breadcrumb')]//a")
    prodCategories = []
    for i in range(1, len(catNodes)):
        cid = register_category(categories, categoriescsv, catNodes[i].text)
        if cid:
            prodCategories.append(cid)

    product["categories"] = [{"id": cid} for cid in prodCategories]
    if len(prodCategories):
        product["main_category"] = prodCategories[-1]


    priceNode = pageTree.xpath("//div[contains(@class,'summary')]//bdi")
    product["price"] = priceNode[0].text[:-1].replace(",",".")




    imgNode = pageTree.xpath("//div[contains(@class,'feat_image')]/a[1]")
    imgURL = imgNode[0].get("href")
    imgOutFileName = "images/" + hashlib.sha1(imgURL.encode('utf-8')).hexdigest() + "." + imgURL.split('.')[-1]

    imgFileData = get_resource(imgURL)
    with open("output/"+imgOutFileName, "wb") as imgOutFile:
        imgOutFile.write(imgFileData)
    product["img"] = imgOutFileName

    fetchResult.append(product)
progCounter.end()

'''
with open("output/items.csv", "w") as fcsv:
    fieldnames = fetchResult[0].keys()
    writer = csv.DictWriter(fcsv, delimiter=';', fieldnames=fieldnames)

    writer.writeheader()
    writer.writerows(fetchResult)

with open("output/categories.csv", "w") as fcsv:
    fieldnames = ["ID", "ParentID", "Name"]
    writer = csv.DictWriter(fcsv, delimiter=';', fieldnames=fieldnames)

    writer.writeheader()
    writer.writerows(categoriescsv)
'''

print("Saving JSON...")

with open("output/categories.json", "w") as jsonFile:
    jsonFile.write(json.dumps(categoriescsv, indent=4))

with open("output/items.json", "w") as jsonFile:
    jsonFile.write(json.dumps(fetchResult, indent=4))

print("done")
