import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ContactClientDetailComponent } from './contact-client-detail.component';

describe('ContactClient Management Detail Component', () => {
  let comp: ContactClientDetailComponent;
  let fixture: ComponentFixture<ContactClientDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ContactClientDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ contactClient: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(ContactClientDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ContactClientDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load contactClient on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.contactClient).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
