import os.path
import shutil

project_folder = './frontend'
pm_option = '{{cookiecutter.pm_option}}'
js_option = '{{cookiecutter.js_option}}'

if os.path.isfile('{{cookiecutter.pm_config_file}}'):
    if pm_option == "npm":
        shutil.copy2('{{cookiecutter.pm_config_file}}', project_folder + "/.npmrc")
    if pm_option == "yarn":
        shutil.copy2('{{cookiecutter.pm_config_file}}', project_folder + "/.yarnrc")

if os.path.isfile('{{cookiecutter.js_config_file}}'):
    if js_option == "flow":
        shutil.copy2('{{cookiecutter.js_config_file}}', project_folder + "/.flowconfig")
    if js_option == "typescript":
        shutil.copy2('{{cookiecutter.js_config_file}}', project_folder + "/tsconfig.json")