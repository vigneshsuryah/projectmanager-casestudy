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
  selector: 'app-updateviewproject',
  templateUrl: 'updateviewproject.component.html',
  styleUrls: ['updateviewproject.component.css']
})
export class UpdateViewProjectComponent implements OnInit, OnDestroy {

  @ViewChild('instance') instance: NgbTypeahead;
  focus$ = new Subject<string>();
  click$ = new Subject<string>();
 
  project : any = {};
  hoveredDate: NgbDate;
  fromDate: NgbDate;
  toDate: NgbDate;
  calendarToday: NgbCalendar
  allProjectMasterList : any = [];
  allProjectList : any = [];
  allUsersList : any = [];
  errorShow : boolean = false;
  screenLoader : boolean = false;
  checkBoxSelect : boolean = false;
  errorMessage : string = '';
  modalHeading : string = '';
  modalBody : string = '';
  flow : string = 'addproject';
  selectedUserObj : any = {}; 
  searchProjectString : string = '';

  constructor(calendar: NgbCalendar, config: NgbDatepickerConfig, public router: Router, private appService : appService) {
    this.calendarToday = calendar;
    
    this.project = {
      "projectName":"",
      "priority":"15",
      "startDate":new Date(),
      "endDate":new Date(),
      "managerName":"",
      "managerId":""
    };
    this.fromDate = calendar.getToday();
    this.toDate = calendar.getNext(calendar.getToday(), 'd', 10);

    const currentDate = new Date();
    config.minDate = {year:currentDate.getFullYear(), month:currentDate.getMonth()+1, day: currentDate.getDate()};
    config.maxDate = {year: 2099, month: 12, day: 31};
    config.outsideDays = 'hidden';

    this.screenLoader = true;
    appService.getUsers().subscribe((data :any) => {
      this.allUsersList = data;
      this.screenLoader = false;
    });
    appService.getProjects().subscribe((data :any) => {
      this.allProjectMasterList = data;
      this.allProjectList = data;
      this.screenLoader = false;
    });

  }

  ngOnInit() {
    
  }

  ngOnDestroy() {
    this.project = {};  
    this.allProjectList = [];  
    this.allUsersList = [];  
  }

  formatter = (value: any) => value.taskName || '';

  

  endProject(project: any){
    this.screenLoader = true;
    project.status = 'Completed';
    this.appService.updateProjects(project).subscribe(
      (data: any) => {
        this.screenLoader = false;
        this.modalHeading = 'Yeah :-)';
        this.modalBody = 'Project Suspended Successfully';
        document.getElementById("submitModalOpener").click();
        this.appService.getProjects().subscribe((data :any) => {
          this.allProjectMasterList = data;
          this.allProjectList = data;
        });
      },
      (err: any) => {
          this.screenLoader = false;
          this.modalHeading = 'Oh No !!!';
          this.modalBody = 'Unexpected error occured during End Task. Please try after some time.';
          document.getElementById("submitModalOpener").click();  
          this.appService.getProjects().subscribe((data :any) => {
            this.allProjectMasterList = data;
            this.allProjectList = data;
          });      
        }
      );
  }

  /* sort functions*/
  sortByStartDate(){
    this.allProjectList = [];
    this.allProjectList = this.allProjectMasterList;
    this.allProjectList.sort((a, b) => {
      return new Date(a.startDate).getTime() - new Date(b.startDate).getTime();
    });
  }

  sortByEndDate(){
    this.allProjectList = [];
    this.allProjectList = this.allProjectMasterList;
    this.allProjectList.sort((a, b) => {
      return new Date(a.endDate).getTime() - new Date(b.endDate).getTime();
    });
  }

  sortByPriority(){
    this.allProjectList = [];
    this.allProjectList = this.allProjectMasterList;
    this.allProjectList.sort((a, b) => {
      return parseInt(a.priority) - parseInt(b.priority);
    });
  }

  sortByStatus(){
    this.allProjectList = [];
    this.allProjectList = this.allProjectMasterList;
    this.allProjectList.sort((a, b) => {
      var titleA = a.status.toLowerCase(), titleB = b.status.toLowerCase();
      if (titleA < titleB) return -1; 
      if (titleA > titleB) return 1;
      return 0;
    });
  }

  /* sort functions*/

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
  /* Datepicker functions*/

}



