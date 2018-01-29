import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CadastradosComponent } from './cadastrados.component';

describe('CadastradosComponent', () => {
  let component: CadastradosComponent;
  let fixture: ComponentFixture<CadastradosComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CadastradosComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CadastradosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
