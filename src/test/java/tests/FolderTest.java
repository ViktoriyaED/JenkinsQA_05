package tests;

import model.folder.FolderStatusPage;
import model.HomePage;
import org.testng.Assert;
import org.testng.annotations.Test;
import runner.BaseTest;
import runner.TestUtils;

import java.util.List;

public class FolderTest extends BaseTest {
    final String FOLDER_RANDOM_NAME_1 = TestUtils.getRandomStr();
    final String FOLDER_RANDOM_NAME_2 = TestUtils.getRandomStr();
    final String DISPLAY_RANDOM_NAME = TestUtils.getRandomStr();

    @Test
    public void testCreateFolder() {
        List<String> projectNamesOnDashboard = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(FOLDER_RANDOM_NAME_1)
                .selectFolderAndClickOk()
                .clickSaveButton()
                .clickDashboard()
                .getJobList();

        Assert.assertTrue(projectNamesOnDashboard.contains(FOLDER_RANDOM_NAME_1));
    }

    @Test(dependsOnMethods = "testCreateFolder")
    public void testCreateMultiConfigurationProjectInFolder() {
        final String multiConfigurationProjectName = TestUtils.getRandomStr();

        FolderStatusPage folderStatusPage = new HomePage(getDriver())
                .clickFolder(FOLDER_RANDOM_NAME_1)
                .clickCreateJob()
                .setItemName(multiConfigurationProjectName)
                .selectMultiConfigurationProjectAndClickOk()
                .clickSave()
                .clickParentFolderInBreadcrumbs();

        Assert.assertTrue(folderStatusPage.getJobList().contains(multiConfigurationProjectName));
    }

    @Test(dependsOnMethods = "testCreateMultiConfigurationProjectInFolder")
    public void testConfigureChangeFolderDisplayName() {
        List<String> projectNamesOnDashboard = new HomePage(getDriver())
                .clickJobDropDownMenu(FOLDER_RANDOM_NAME_1)
                .clickConfigureDropDownMenuForFolder()
                .setDisplayName(DISPLAY_RANDOM_NAME)
                .setDescription("change name")
                .clickSaveButton()
                .clickDashboard()
                .getJobList();

        Assert.assertTrue(projectNamesOnDashboard.contains(DISPLAY_RANDOM_NAME));
    }

    @Test(dependsOnMethods = "testConfigureChangeFolderDisplayName")
    public void testConfigureFolderDisplayNameSaveFolderName() {
        String folderStatusPage = new HomePage(getDriver())
                .clickJob(DISPLAY_RANDOM_NAME)
                .getFolderName();

        Assert.assertEquals(folderStatusPage, "Folder name: " + FOLDER_RANDOM_NAME_1);
    }

    @Test(dependsOnMethods = "testConfigureFolderDisplayNameSaveFolderName")
    public void testConfigureFolderAddDescription() {
        final String addDescription = "Add description";

        String textDescription = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(FOLDER_RANDOM_NAME_2)
                .selectFolderAndClickOk()
                .setDescription(addDescription)
                .clickSaveButton()
                .getTextDescription();

        Assert.assertEquals(textDescription, addDescription);
    }

    @Test(dependsOnMethods = "testConfigureFolderAddDescription")
    public void testMoveFolderInFolder() {
        List<String> foldersNamesInFolder = new HomePage(getDriver())
                .clickDashboard()
                .clickJob(FOLDER_RANDOM_NAME_2)
                .clickMoveFolder()
                .selectFolder(DISPLAY_RANDOM_NAME)
                .clickMove()
                .clickDashboard()
                .clickJob(DISPLAY_RANDOM_NAME)
                .getJobList();

        Assert.assertTrue(foldersNamesInFolder.contains(FOLDER_RANDOM_NAME_2));
    }

    @Test(dependsOnMethods = "testMoveFolderInFolder")
    public void testDeleteFolder() {
        String pageHeaderText = new HomePage(getDriver())
                .clickDashboard()
                .clickJob(DISPLAY_RANDOM_NAME)
                .clickDeleteFolder()
                .clickSubmitButton()
                .getHeaderText();

        Assert.assertEquals(pageHeaderText, "Welcome to Jenkins!");
    }

    @Test
    public void testNameAfterRenameInFolder() {
        List<String> newFolderName = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(FOLDER_RANDOM_NAME_1)
                .selectFolderAndClickOk()
                .clickDashboard()
                .clickFolder(FOLDER_RANDOM_NAME_1)
                .clickRename(FOLDER_RANDOM_NAME_1)
                .clearAndSetNewName(FOLDER_RANDOM_NAME_2)
                .clickSubmitButton()
                .clickDashboard()
                .getJobList();

        Assert.assertTrue(newFolderName.contains(FOLDER_RANDOM_NAME_2));
    }

    @Test(dependsOnMethods = "testNameAfterRenameInFolder")
    public void testCreateFreestyleProjectInFolderCreateJob() {
        final String freestyleProjectName = TestUtils.getRandomStr();

        List<String> projectNamesInFolder = new HomePage(getDriver())
                .clickFolder(FOLDER_RANDOM_NAME_2)
                .clickCreateJob()
                .setItemName(freestyleProjectName)
                .selectFreestyleProjectAndClickOk()
                .clickSaveBtn()
                .clickDashboard()
                .clickFolder(FOLDER_RANDOM_NAME_2)
                .getJobList();

        Assert.assertTrue(projectNamesInFolder.contains(freestyleProjectName));
    }

    @Test(dependsOnMethods = "testCreateFreestyleProjectInFolderCreateJob")
    public void testMoveFreestyleProjectInFolderUsingDropDownMenu() {
        final String freestyleProjectName = TestUtils.getRandomStr();

        List<String> projectNamesInFolder = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(freestyleProjectName)
                .selectFreestyleProjectAndClickOk()
                .clickSaveBtn()
                .clickDashboard()
                .clickJobDropDownMenu(freestyleProjectName)
                .clickMoveButtonDropdown()
                .selectFolder(FOLDER_RANDOM_NAME_2)
                .clickMove()
                .clickDashboard()
                .clickFolder(FOLDER_RANDOM_NAME_2)
                .getJobList();

        Assert.assertTrue(projectNamesInFolder.contains(freestyleProjectName));
    }

    @Test(dependsOnMethods = "testMoveFreestyleProjectInFolderUsingDropDownMenu")
    public void testCreateFreestyleProjectInFolderNewItem() {
        final String freestyleProjectName = TestUtils.getRandomStr();

        List<String> projectNamesInFolder = new HomePage(getDriver())
                .clickFolder(FOLDER_RANDOM_NAME_2)
                .clickFolderNewItem()
                .setItemName(freestyleProjectName)
                .selectFreestyleProjectAndClickOk()
                .clickSaveBtn()
                .clickDashboard()
                .clickFolder(FOLDER_RANDOM_NAME_2)
                .getJobList();

        Assert.assertTrue(projectNamesInFolder.contains(freestyleProjectName));
    }

    @Test
    public void testCreateFolderInFolder() {
        List<String> folderNamesInFolder = new HomePage(getDriver())
                .clickNewItem()
                .setItemName(FOLDER_RANDOM_NAME_1)
                .selectFolderAndClickOk()
                .clickSaveButton()
                .clickCreateJob()
                .setItemName(FOLDER_RANDOM_NAME_2)
                .selectFolderAndClickOk()
                .clickSaveButton()
                .clickDashboard()
                .clickFolder(FOLDER_RANDOM_NAME_1)
                .getJobList();

        Assert.assertTrue(folderNamesInFolder.contains(FOLDER_RANDOM_NAME_2));
    }

    @Test(dependsOnMethods = "testCreateFolderInFolder")
    public void testAddFolderDescription() {
        String folderDescription = TestUtils.getRandomStr();

        String textDescription = new HomePage(getDriver())
                .clickDashboard()
                .clickFolder(FOLDER_RANDOM_NAME_1)
                .clickAddDescription()
                .setDescription(folderDescription)
                .clickSubmitButton()
                .clickDashboard()
                .clickFolder(FOLDER_RANDOM_NAME_1)
                .getTextDescriptionOnPage();

        Assert.assertEquals(textDescription, folderDescription);
    }

    @Test(dependsOnMethods = "testAddFolderDescription")
    public void testCreateFreestyleProjectInFolderByNewItemDropDownInCrambMenu() {
        final String freestyleProjectName = TestUtils.getRandomStr();

        List<String> projectNamesInFolder = new HomePage(getDriver())
                .clickFolder(FOLDER_RANDOM_NAME_1)
                .clickNewItemDropdownThisFolderInBreadcrumbs()
                .setItemName(freestyleProjectName)
                .selectFreestyleProjectAndClickOk()
                .clickSaveBtn()
                .clickParentFolderInBreadcrumbs()
                .getJobList();

        Assert.assertTrue(projectNamesInFolder.contains(freestyleProjectName));
    }

    @Test(dependsOnMethods = "testCreateFreestyleProjectInFolderByNewItemDropDownInCrambMenu")
    public void testCreateMultibranchPipelineProjectInFolder() {
        final String multibranchPipelineProjectName = TestUtils.getRandomStr();

        List<String> projectNamesInFolder = new HomePage(getDriver())
                .clickFolder(FOLDER_RANDOM_NAME_1)
                .clickFolderNewItem()
                .setItemName(multibranchPipelineProjectName)
                .selectMultibranchPipelineAndClickOk()
                .clickSaveButton()
                .clickDashboard()
                .clickFolder(FOLDER_RANDOM_NAME_1)
                .getJobList();

        Assert.assertTrue(projectNamesInFolder.contains(multibranchPipelineProjectName));
    }

    @Test(dependsOnMethods = "testCreateMultibranchPipelineProjectInFolder")
    public void testDeleteFolderUsingDropDown() {
        String welcomeJenkinsHeader = new HomePage(getDriver())
                .clickDashboard()
                .clickJobDropDownMenu(FOLDER_RANDOM_NAME_1)
                .clickDeleteDropDownMenu()
                .clickSubmitDeleteProject()
                .getHeaderText();

        Assert.assertEquals(welcomeJenkinsHeader, "Welcome to Jenkins!");
    }
}