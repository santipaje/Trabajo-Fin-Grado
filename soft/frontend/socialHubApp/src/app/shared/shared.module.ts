import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Error404Component } from './pages/error404/error404.component'
import { NavbarComponent } from './pages/navbar/navbar.component'
import { FormsModule } from '@angular/forms';
import { SidebarComponent } from './pages/sidebar/sidebar.component';


@NgModule({
  declarations: [
    Error404Component,
    NavbarComponent,
    SidebarComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
  ],
  exports: [
    Error404Component,
    NavbarComponent,
    SidebarComponent
  ]
})
export class SharedModule { }
