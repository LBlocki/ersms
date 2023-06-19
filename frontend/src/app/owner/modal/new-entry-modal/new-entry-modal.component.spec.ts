import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NewEntryModalComponent } from './new-entry-modal.component';

describe('NewEntryModalComponent', () => {
  let component: NewEntryModalComponent;
  let fixture: ComponentFixture<NewEntryModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NewEntryModalComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(NewEntryModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
