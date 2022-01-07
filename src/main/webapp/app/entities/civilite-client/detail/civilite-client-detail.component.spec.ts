import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CiviliteClientDetailComponent } from './civilite-client-detail.component';

describe('CiviliteClient Management Detail Component', () => {
  let comp: CiviliteClientDetailComponent;
  let fixture: ComponentFixture<CiviliteClientDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CiviliteClientDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ civiliteClient: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(CiviliteClientDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(CiviliteClientDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load civiliteClient on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.civiliteClient).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
