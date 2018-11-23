import { Component, OnInit, Inject, ViewEncapsulation, OnDestroy, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { NgForm } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { DOCUMENT } from '@angular/platform-browser';
import { NgbTypeahead, NgbTypeaheadSelectItemEvent, NgbDatepicker, NgbDatepickerConfig, NgbDate, NgbCalendar, NgbDateStruct} from '@ng-bootstrap/ng-bootstrap';
import { Observable, Subject, merge} from 'rxjs';
import { debounceTime, distinctUntilChanged, filter, map} from 'rxjs/operators';
import { appService } from '../service';
declare var jQuery:any;

@Component({

  selector: 'app-updatetask',
  templateUrl: 'updatetask.component.html',
  styleUrls: ['updatetask.component.css']
})
export class UpdateTaskComponent implements OnInit, OnDestroy {

  @ViewChild('instanceProject') instanceProject: NgbTypeahead;
  @ViewChild('instanceParentTask') instanceParentTask: NgbTypeahead;
  @ViewChild('instanceUser') instanceUser: NgbTypeahead;
  focusUser$ = new Subject<string>();
  focusProject$ = new Subject<string>();
  focusParentTask$ = new Subject<string>();
  clickUser$ = new Subject<string>();
  clickProject$ = new Subject<string>();
  clickParentTask$ = new Subject<string>();
 
  task : any = {};
  hoveredDate: NgbDate;
  fromDate: NgbDate;
  toDate: NgbDate;
  calendarToday: NgbCalendar
  alltaskList : any = [];
  allProjectList : any = [];
  allParentTaskList : any = [];
  allUsersList : any = [];
  errorShow : boolean = false;
  screenLoader : boolean = false;
  errorMessage : string = '';
  modalHeading : string = '';
  modalBody : string = '';
  flow : string = 'addtask';
  selectedParentTaskObj : any = {};
  selectedProjectObj : any = {};
  selectedUserObj : any = {};
  isParentTaskSelection : boolean = false;

  constructor(calendar: NgbCalendar, config: NgbDatepickerConfig, public router: Router, private appService : appService) {
    this.calendarToday = calendar;
    if(this.appService.updatetask !== null){
      this.flow = 'updatetask';
    }
    this.screenLoader = true;
    appService.getProjects().subscribe((data :any) => {
      this.allProjectList = data;
      this.screenLoader = false;
    });
    this.screenLoader = true;
    appService.getParentTask().subscribe((data :any) => {
      this.allParentTaskList = data;
      this.screenLoader = false;
    });
    this.screenLoader = true;
    appService.getUsers().subscribe((data :any) => {
      this.allUsersList = data;
      for ( var i = 0; i < this.allUsersList.length; i++)
      {
        this.allUsersList[i].fullName = this.allUsersList[i].lastName + ", " + this.allUsersList[i].firstName;
      }
      this.screenLoader = false;
    });
    if(this.flow === 'addtask'){
      this.task = {
        "taskName":"",
        "priority":"15",
        "parentTaskId":"",
        "parentTaskName":"",
        "startDate":new Date(),
        "endDate":new Date()
      };
      this.fromDate = calendar.getToday();
      this.toDate = calendar.getNext(calendar.getToday(), 'd', 10);
    }

    if(this.flow === 'updatetask'){
      var edittask = this.appService.updatetask;
      this.selectedParentTaskObj = edittask.parentTask !== null ? edittask.parentTask : null;
      this.task = {
        "taskName":edittask.taskName,
        "priority":edittask.priority,
        "parentTaskId":edittask.parentTask !== null ? edittask.parentTask.taskId : '',
        "parentTaskName":edittask.parentTask !== null ? edittask.parentTask.taskName : '',
        "startDate":new Date(),
        "endDate":new Date()
      };
      this.fromDate = NgbDate.from(this.constructDateFromService(edittask.startDate));
      this.toDate = NgbDate.from(this.constructDateFromService(edittask.endDate));
    }

    const currentDate = new Date();
    config.minDate = {year:currentDate.getFullYear(), month:currentDate.getMonth()+1, day: currentDate.getDate()};
    config.maxDate = {year: 2099, month: 12, day: 31};
    config.outsideDays = 'hidden';

    this.screenLoader = true;
    appService.getTasks().subscribe((data :any) => {
      this.alltaskList = data;
      this.screenLoader = false;
    });
  }

  ngOnInit() {
    
  }

  ngOnDestroy() {
    this.task = {};
  }

  formatter = (value: any) => value.taskName || '';

  updateTask(task: any){
    var parentTaskNameVal = jQuery("#parentTask").val();
    this.errorShow = false;
    this.errorMessage = '';
    if(parentTaskNameVal !== '' && this.task.parentTaskId === ''){
      this.errorShow = true;
      this.errorMessage = 'Select Parent Task from the list available. Either the task name is edited or you have typed a custom task name.';
    }else{
      if(this.flow === 'addtask'){
        var submitAddTask = {};
        if(this.task.parentTaskId === '' || this.task.parentTaskId === null || this.task.parentTaskId === undefined){
          submitAddTask = {
            "taskName": this.task.taskName,
            "startDate": this.convertDateJsonToString(this.fromDate),
            "endDate": this.convertDateJsonToString(this.toDate),
            "priority": this.task.priority,
            "status": "A"
          };
        }else{
          submitAddTask = {
            "taskName": this.task.taskName,
            "startDate": this.convertDateJsonToString(this.fromDate),
            "endDate": this.convertDateJsonToString(this.toDate),
            "priority": this.task.priority,
            "status": "A",
            "parentTask": {
              "taskId" : this.task.parentTaskId === '' ? null: this.task.parentTaskId
            }
          };
        }
        
        this.screenLoader = true;
        this.appService.addTask(submitAddTask).subscribe(
          (data: any) => {
            this.screenLoader = false;
            this.modalHeading = 'Yeah :-)';
            this.modalBody = 'Task Added Successfully';
            document.getElementById("submitModalOpener").click();
          },
          (err: any) => {
              this.screenLoader = false;
              this.modalHeading = 'Oh No !!!';
              this.modalBody = 'Unexpected error occured during Add Task. Please try after some time.';
              document.getElementById("submitModalOpener").click();        
            }
          );
      }
      if(this.flow === 'updatetask'){
        var submitUpdateTask = {
          "taskId": this.appService.updatetask.taskId,
          "taskName": this.task.taskName,
          "startDate": this.convertDateJsonToString(this.fromDate),
          "endDate": this.convertDateJsonToString(this.toDate),
          "priority": this.task.priority,
          "status": "A",
          "parentTask": this.selectedParentTaskObj
        };
        this.screenLoader = true;
        this.appService.editTask(submitUpdateTask, this.appService.updatetask.taskId).subscribe(
          (data: any) => {
            this.screenLoader = false;
            this.modalHeading = 'Yeah :-)';
            this.modalBody = 'Task Updated Successfully';
            document.getElementById("submitModalOpener").click();
          },
          (err: any) => {
              this.screenLoader = false;
              this.modalHeading = 'Oh No !!!';
              this.modalBody = 'Unexpected error occured during Update Task. Please try after some time.';
              document.getElementById("submitModalOpener").click();        
            }
          );
      }
    }
  }

  constructDateFromService(datestring: string){
    var res = datestring.split("/");
    const date: NgbDateStruct = { day: parseInt(res[0]), month: parseInt(res[1]), year: parseInt(res[2]) };
    return date;
  }

  resetButton(){
    this.task = {
      "taskName":"",
      "priority":"15",
      "parentTaskId":"",
      "parentTaskName":"",
      "startDate":new Date(),
      "endDate":new Date()
    };
    this.fromDate = this.calendarToday.getToday();
    this.toDate = this.calendarToday.getNext(this.calendarToday.getToday(), 'd', 10);
    jQuery("#parentTask").val("");
  }
  
  viewTaskScreen(){
    document.getElementById("view-task").click();
  }

  /* Datepicker functions*/
  onDateSelection(date: NgbDate) {
    if (!this.fromDate && !this.toDate) {
      this.fromDate = date;
    } else if (this.fromDate && !this.toDate && date.after(this.fromDate)) {
      this.toDate = date;
    } else {
      this.toDate = null;
      this.fromDate = date;
    }
  }

  isHovered(date: NgbDate) {
    return this.fromDate && !this.toDate && this.hoveredDate && date.after(this.fromDate) && date.before(this.hoveredDate);
  }

  isInside(date: NgbDate) {
    return date.after(this.fromDate) && date.before(this.toDate);
  }

  isRange(date: NgbDate) {
    return date.equals(this.fromDate) || date.equals(this.toDate) || this.isInside(date) || this.isHovered(date);
  }

  convertDateJsonToString(json: any){
    if(json !== undefined && json !== null){
      return json.day + '/' + json.month + '/' + json.year;
    }
  }

  
  searchProjectPopup(){
    jQuery("#projectSelectOpener").click();
  }

  projectSearchAhead = (text$: Observable<string>) => {
    const debouncedText$ = text$.pipe(debounceTime(200), distinctUntilChanged());
    const clicksWithClosedPopup$ = this.clickProject$.pipe(filter(() => !this.instanceProject.isPopupOpen()));
    const inputFocus$ = this.focusProject$;
    return merge(debouncedText$, inputFocus$, clicksWithClosedPopup$).pipe(
      map(term => (term === '' ? this.allProjectList : this.allProjectList.filter(v => v.projectName.toLowerCase().indexOf(term.toLowerCase()) > -1)).slice(0, 10))
    );
  }

  formatterProjectName = (value: any) => value.projectName || '';

  selectedProjectItem(event: NgbTypeaheadSelectItemEvent): void {
    event.preventDefault();
    var projectDetails = event.item;
    this.task.projectName = projectDetails.projectName;
    jQuery("#projectSelectId").val(this.task.projectName);
    this.task.projectId = projectDetails.projectId;
  }

  clearProjectItem(event){
    if (event.key !== "Enter") {
      this.task.projectName = "";
      this.task.projectId = "";
    }
  }

  searchParentTaskPopup(){
    jQuery("#parentTaskSelectOpener").click();
  }

  parentTaskSearchAhead = (text$: Observable<string>) => {
    const debouncedText$ = text$.pipe(debounceTime(200), distinctUntilChanged());
    const clicksWithClosedPopup$ = this.clickParentTask$.pipe(filter(() => !this.instanceParentTask.isPopupOpen()));
    const inputFocus$ = this.focusParentTask$;
    return merge(debouncedText$, inputFocus$, clicksWithClosedPopup$).pipe(
      map(term => (term === '' ? this.allParentTaskList : this.allParentTaskList.filter(v => v.parentTaskName.toLowerCase().indexOf(term.toLowerCase()) > -1)).slice(0, 10))
    );
  }

  formatterParentTaskName = (value: any) => value.parentTaskName || '';

  selectedParentTaskItem(event: NgbTypeaheadSelectItemEvent): void {
    event.preventDefault();
    var parentTaskDetails = event.item;
    this.selectedParentTaskObj = event.item;
    this.task.parentTaskName = parentTaskDetails.parentTaskName;
    //jQuery("#projectSelectId").val(this.task.projectName);
    this.task.parentTaskName = parentTaskDetails.parentTaskName;
  }

  clearParentTaskItem(event){
    if (event.key !== "Enter") {
      this.task.parentTaskName = "";
      this.task.parentTaskId = "";
    }
  }

  searchUserPopup(){
    jQuery("#parentTaskSelectOpener").click();
  }

  userEmployeeSearch = (text$: Observable<string>) => {
    const debouncedText$ = text$.pipe(debounceTime(200), distinctUntilChanged());
    const clicksWithClosedPopup$ = this.clickUser$.pipe(filter(() => !this.instanceUser.isPopupOpen()));
    const inputFocus$ = this.focusUser$;
    return merge(debouncedText$, inputFocus$, clicksWithClosedPopup$).pipe(
      map(term => (term === '' ? this.allUsersList : this.allUsersList.filter(v => v.fullName.toLowerCase().indexOf(term.toLowerCase()) > -1)).slice(0, 10))
    );
  }

  formatterUserSelect = (value: any) => (value.lastName + ", " + value.firstName) || '';

  selectedUserItem(event: NgbTypeaheadSelectItemEvent): void {
    event.preventDefault();
    var userDetails = event.item;
    this.selectedParentTaskObj = event.item;
    this.task.userName = userDetails.fullName;
    //jQuery("#projectSelectId").val(this.task.projectName);
    //this.task.userName = userDetails.fullName;
  }

  clearUserItem(event){
    if (event.key !== "Enter") {
      this.task.userName = "";
      this.task.userId = "";
    }
  }

}



