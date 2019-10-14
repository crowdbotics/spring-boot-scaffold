import os.path
import shutil

project_folder = './frontend'
pm_option = '{{cookiecutter.pm_option}}'

#'upload/.yarnrc'
if os.path.isfile('{{cookiecutter.pm_config_file}}'):
    if pm_option == "npm":
        shutil.copy2('{{cookiecutter.pm_config_file}}', project_folder + "/.npmrc")
    if pm_option == "yarn":
        shutil.copy2('{{cookiecutter.pm_config_file}}', project_folder + "/.yarnrc")